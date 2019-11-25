package com.moebius.tracker.domain.trades

import com.moebius.tracker.domain.ElasticDocumentRepository
import com.moebius.tracker.utils.ElasticUtils
import java.time.LocalDateTime

interface TradeStatsDocumentRepository : ElasticDocumentRepository<TradeStatsDocument> {
    /**
     * generate trade stats
     * startDatetime to interval time
     */
    fun generateTradeStats(startDateTime: LocalDateTime, interval: ElasticUtils.AggregationInterval) : List<TradeStatsDocument>
}