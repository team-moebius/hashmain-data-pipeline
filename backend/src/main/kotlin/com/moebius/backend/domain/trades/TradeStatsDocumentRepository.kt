package com.moebius.backend.domain.trades

import com.moebius.backend.domain.ElasticDocumentRepository
import com.moebius.backend.domain.commons.Exchange
import com.moebius.backend.utils.ElasticUtils
import java.time.LocalDateTime

interface TradeStatsDocumentRepository : ElasticDocumentRepository<TradeStatsDocument> {
    /**
     * generate trade stats
     * startDatetime to interval time
     */
    fun generateTradeStats(startDateTime: LocalDateTime, interval: ElasticUtils.AggregationInterval) : List<TradeStatsDocument>
}