package com.moebius.backend.domain.trades

import com.moebius.backend.domain.commons.DocumentIndex
import com.moebius.backend.domain.commons.Exchange
import com.moebius.backend.domain.commons.TradeType
import com.moebius.backend.utils.ElasticUtils
import mu.KotlinLogging
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.search.aggregations.AggregationBuilder
import org.elasticsearch.search.aggregations.AggregationBuilders
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
                          private val marketCount: Long) {
    private val log = KotlinLogging.logger {}

    private enum class AGGNAMES(name: String) {
        HISTOGRAM("histogram"),
        FILTER("filter"),
        TRX_SUM("transaction_sum"),
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

    private fun statsTransactionSum(): AggregationBuilder = AggregationBuilders.sum(AGGNAMES.TRX_SUM.name).field("price")

    private fun statsTermsAggregation(): Array<AggregationBuilder> {
        log.info("Exchange aggregation size : ${Exchange.values().size}")
        log.info("Symbol aggregation size : $marketCount")
        log.info("TradeType aggregation size : ${TradeType.values().size}")
        return arrayOf(
                AggregationBuilders.terms(AGGNAMES.EXCHANGE.name).field("exchange").size(Exchange.values().size),
                AggregationBuilders.terms(AGGNAMES.SYMBOL.name).field("symbol").size(marketCount.toInt()),
                AggregationBuilders.terms(AGGNAMES.TRADE_TYPE.name).field("tradeType").size(TradeType.values().size)
        )
    }

    private fun aggregationSearchQuery(): SearchRequest {
        val aggregation = listOf(
                statsFilterAggregation(),
                statsDateHistogramAggregation(),
                *statsTermsAggregation(),
                statsTransactionSum()
        ).run { ElasticUtils.aggregationTreeRequest(this) }

        return SearchRequest(DocumentIndex.tradeStream.searchIndex()).source(
                SearchSourceBuilder.searchSource().aggregation(aggregation).size(0)
        )
    }

    private fun deserializeResponse(response: SearchResponse): List<TradeStatsDocument> {
        val filter = response.aggregations.get<ParsedFilter>(AGGNAMES.FILTER.name)
        log.info("range total doc count: ${filter.docCount}")
        val dateHistogram = filter.aggregations.get<ParsedDateHistogram>(AGGNAMES.HISTOGRAM.name)
        return dateHistogram.buckets.map { parseHistogram(it) }.flatten()
    }


    private fun parseHistogram(aggDate: Histogram.Bucket): List<TradeStatsDocument> {
        log.info("date: ${aggDate.keyAsString} (${aggDate.key}) doc-count: ${aggDate.docCount}")
        val aggExchanges = aggDate.aggregations.get<ParsedStringTerms>(AGGNAMES.EXCHANGE.name)
        val parsedDate = aggDate.key as ZonedDateTime
        return aggExchanges.buckets.map { parseExchange(it) }.flatten().map { it.statsDate(parsedDate).build() }
    }

    private fun parseExchange(aggExchange: Terms.Bucket): List<TradeStatsDocument.Builder> {
        log.info("exchange: ${aggExchange.keyAsString} doc-count: ${aggExchange.docCount}")
        val exchange = Exchange.valueOf(aggExchange.keyAsString)
        val aggSymbols = aggExchange.aggregations.get<ParsedStringTerms>(AGGNAMES.SYMBOL.name)
        return aggSymbols.buckets.map { parseSymbol(it) }.map { it.exchange(exchange) }
    }

    private fun parseSymbol(aggSymbol: Terms.Bucket): TradeStatsDocument.Builder {
        log.info("symbol: ${aggSymbol.keyAsString} doc-count: ${aggSymbol.docCount}")
        val symbol = aggSymbol.keyAsString
        val docCount = aggSymbol.docCount
        val aggTradeTypes = aggSymbol.aggregations.get<ParsedStringTerms>(AGGNAMES.TRADE_TYPE.name)
        val document = TradeStatsDocument.Builder().symbol(symbol).timeUnit(interval)

        val summaryList = aggTradeTypes.buckets.map { parseTradeTypeAndSum(it) }

        summaryList.forEach {
            when (it.first) {
                TradeType.BID -> document.totalBidCount(it.second).totalBidPrice(it.third)
                TradeType.ASK -> document.totalAskCount(it.second).totalAskPrice(it.third)
            }
        }

        summaryList.sumByDouble { it.third }.apply {
            document.totalTransactionCount(docCount).totalTransactionPrice(this)
        }
        return document
    }

    private fun parseTradeTypeAndSum(aggTradeType: Terms.Bucket): Triple<TradeType, Long, Double> {
        log.info("tradeType: ${aggTradeType.keyAsString} doc-count: ${aggTradeType.docCount}")
        val tradeType = TradeType.valueOf(aggTradeType.keyAsString)
        val docCount = aggTradeType.docCount
        val sumValue = aggTradeType.aggregations.get<ParsedSum>(AGGNAMES.TRX_SUM.name).value()
        return Triple(tradeType, docCount, sumValue)
    }

    fun generate(client: RestHighLevelClient): List<TradeStatsDocument> {
        val query = aggregationSearchQuery()
        val response = client.search(query, RequestOptions.DEFAULT)
        return deserializeResponse(response)
    }
}