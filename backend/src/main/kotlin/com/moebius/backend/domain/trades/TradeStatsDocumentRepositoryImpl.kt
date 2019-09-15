package com.moebius.backend.domain.trades

import com.moebius.backend.domain.ElasticDocumentRepositoryImpl
import com.moebius.backend.domain.commons.DocumentIndex
import com.moebius.backend.utils.ElasticUtils
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class TradeStatsDocumentRepositoryImpl(@Autowired override val client: RestHighLevelClient) :
        TradeStatsDocumentRepository, ElasticDocumentRepositoryImpl<TradeStatsDocument>(client, DocumentIndex.tradeStats, TradeStatsDocument::class.java) {

    @Autowired
    private var marketCount: Long = 0L

    override fun generateTradeStats(startDateTime: LocalDateTime, interval: ElasticUtils.AggregationInterval): List<TradeStatsDocument> {
        return TradeStatsGenerator(startDateTime, interval, marketCount).generate(client)
    }
}