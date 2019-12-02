package com.moebius.tracker.domain.trades

import com.moebius.tracker.domain.ElasticDocumentRepositoryImpl
import com.moebius.tracker.domain.commons.DocumentIndex
import com.moebius.tracker.utils.ElasticUtils
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class TradeStatsDocumentRepositoryImpl(
        override val restHighLevelClient: RestHighLevelClient,
        private val marketCount: Int
) : TradeStatsDocumentRepository, ElasticDocumentRepositoryImpl<TradeStatsDocument>(restHighLevelClient, DocumentIndex.tradeStats, TradeStatsDocument::class.java) {
    override fun generateTradeStats(startDateTime: LocalDateTime, interval: ElasticUtils.AggregationInterval): List<TradeStatsDocument> {
        return TradeStatsGenerator(startDateTime, interval, marketCount).generate(restHighLevelClient)
    }
}