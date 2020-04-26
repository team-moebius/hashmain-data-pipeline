package com.moebius.tracker.domain.trades

import com.moebius.backend.domain.commons.Exchange
import com.moebius.backend.domain.commons.TradeType
import com.moebius.tracker.domain.commons.DocumentIndex
import com.moebius.tracker.utils.ElasticUtils
import mu.KotlinLogging
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.search.aggregations.AggregationBuilder
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.aggregations.AggregatorFactories
import org.elasticsearch.search.aggregations.bucket.filter.ParsedFilter
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms
import org.elasticsearch.search.aggregations.bucket.terms.Terms
import org.elasticsearch.search.aggregations.metrics.ParsedSum
import org.elasticsearch.search.builder.SearchSourceBuilder
import java.time.LocalDateTime
import java.time.ZonedDateTime

class TradeStatsGenerator(private val startDateTime: LocalDateTime,
                          private val interval: ElasticUtils.AggregationInterval,
                          private val marketCount: Int) {
    private val log = KotlinLogging.logger {}

    private enum class AGGNAMES(name: String) {
        HISTOGRAM("histogram"),
        FILTER("filter"),
        PRICE_SUM("price_sum"),
        VOLUME_SUM("volume_sum"),
        EXCHANGE("exchange_terms"),
        SYMBOL("symbol_terms"),
        TRADE_TYPE("tradeType_terms");
    }

    private fun statsDateHistogramAggregation(): AggregationBuilder = AggregationBuilders.dateHistogram(AGGNAMES.HISTOGRAM.name)
            .dateHistogramInterval(DateHistogramInterval(interval.interval))
            .field("createdAt")

    private fun statsFilterAggregation(): AggregationBuilder {
        val endDateTime = when (interval) {
            ElasticUtils.AggregationInterval.EVERY_MINUTES -> LocalDateTime.from(startDateTime).plusMinutes(1L).minusSeconds(1)
            else -> throw NotImplementedError("must be interval case")
        }

        return AggregationBuilders.filter(AGGNAMES.FILTER.name,
                ElasticUtils.dateTimeRangeQuery("createdAt", startDateTime, endDateTime)
        )
    }

    private fun statsPriceSum(): AggregationBuilder = AggregationBuilders.sum(AGGNAMES.PRICE_SUM.name).field("price")

    private fun statsVolumeSum(): AggregationBuilder = AggregationBuilders.sum(AGGNAMES.VOLUME_SUM.name).field("volume")

    private fun statsTermsAggregation(): Array<AggregationBuilder> {
        log.debug("Exchange aggregation size : ${Exchange.values().size}")
        log.debug("Symbol aggregation size : $marketCount")
        log.debug("TradeType aggregation size : ${TradeType.values().size}")
        return arrayOf(
                AggregationBuilders.terms(AGGNAMES.EXCHANGE.name).field("exchange").size(Exchange.values().size),
                AggregationBuilders.terms(AGGNAMES.SYMBOL.name).field("symbol").size(marketCount),
                tradeTypeTermsAndValueSum()
        )
    }

    private fun tradeTypeTermsAndValueSum(): AggregationBuilder {
        val factories = AggregatorFactories.builder().addAggregator(statsPriceSum()).addAggregator(statsVolumeSum())
        return AggregationBuilders.terms(AGGNAMES.TRADE_TYPE.name).field("tradeType").size(TradeType.values().size)
                .subAggregations(factories)
    }


    private fun aggregationSearchQuery(): SearchRequest {
        val aggregation = listOf(
                statsFilterAggregation(),
                statsDateHistogramAggregation(),
                *statsTermsAggregation()
        ).run { ElasticUtils.aggregationTreeRequest(this) }

        return SearchRequest(DocumentIndex.tradeStream.searchIndex()).source(
                SearchSourceBuilder.searchSource().aggregation(aggregation).size(0)
        )
    }

    private fun deserializeResponse(response: SearchResponse): List<TradeStatsDocument> {
        val filter = response.aggregations.get<ParsedFilter>(AGGNAMES.FILTER.name)
        log.debug("range total doc count: ${filter.docCount}")
        val dateHistogram = filter.aggregations.get<ParsedDateHistogram>(AGGNAMES.HISTOGRAM.name)
        return dateHistogram.buckets.map { parseHistogram(it) }.flatten()
    }

    private fun parseHistogram(aggDate: Histogram.Bucket): List<TradeStatsDocument> {
        log.debug("date: ${aggDate.keyAsString} (${aggDate.key}) doc-count: ${aggDate.docCount}")
        val aggExchanges = aggDate.aggregations.get<ParsedStringTerms>(AGGNAMES.EXCHANGE.name)
        val parsedDate = aggDate.key as ZonedDateTime
        return aggExchanges.buckets.map { parseExchange(it) }.flatten().map { it.statsDate(parsedDate).build() }
    }

    private fun parseExchange(aggExchange: Terms.Bucket): List<TradeStatsDocument.Builder> {
        log.debug("exchange: ${aggExchange.keyAsString} doc-count: ${aggExchange.docCount}")
        val exchange = Exchange.valueOf(aggExchange.keyAsString)
        val aggSymbols = aggExchange.aggregations.get<ParsedStringTerms>(AGGNAMES.SYMBOL.name)
        return aggSymbols.buckets.map { parseSymbol(it) }.map { it.exchange(exchange) }
    }

    private fun parseSymbol(aggSymbol: Terms.Bucket): TradeStatsDocument.Builder {
        log.debug("symbol: ${aggSymbol.keyAsString} doc-count: ${aggSymbol.docCount}")
        val symbol = aggSymbol.keyAsString
        val docCount = aggSymbol.docCount
        val aggTradeTypes = aggSymbol.aggregations.get<ParsedStringTerms>(AGGNAMES.TRADE_TYPE.name)
        val document = TradeStatsDocument.Builder().symbol(symbol).timeUnit(interval)

        val summaryList = aggTradeTypes.buckets.map { parseTradeTypeAndSum(it) }

        summaryList.forEach {
            when (it.tradeType) {
                TradeType.BID -> document.totalBidCount(it.docCount).totalBidPrice(it.priceSum).totalBidVolume(it.volumeSum)
                TradeType.ASK -> document.totalAskCount(it.docCount).totalAskPrice(it.priceSum).totalAskVolume(it.volumeSum)
            }
        }

        document.totalTransactionCount(docCount)
        document.totalTransactionPrice(summaryList.sumByDouble { it.priceSum })
        document.totalTransactionVolume(summaryList.sumByDouble { it.volumeSum })

        return document
    }

    private fun parseTradeTypeAndSum(aggTradeType: Terms.Bucket): ParsedData {
        log.debug("tradeType: ${aggTradeType.keyAsString} doc-count: ${aggTradeType.docCount}")
        val tradeType = TradeType.valueOf(aggTradeType.keyAsString)
        val docCount = aggTradeType.docCount
        val priceSum = aggTradeType.aggregations.get<ParsedSum>(AGGNAMES.PRICE_SUM.name).value()
        val volumeSum = aggTradeType.aggregations.get<ParsedSum>(AGGNAMES.VOLUME_SUM.name).value()
        return ParsedData(tradeType, docCount, priceSum, volumeSum)
    }

    private data class ParsedData(
            val tradeType: TradeType,
            val docCount: Long,
            val priceSum: Double,
            val volumeSum: Double
    )

    fun generate(restHighLevelClient: RestHighLevelClient): List<TradeStatsDocument> {
        val query = aggregationSearchQuery()
        val response = restHighLevelClient.search(query, RequestOptions.DEFAULT)
        return deserializeResponse(response)
    }
}