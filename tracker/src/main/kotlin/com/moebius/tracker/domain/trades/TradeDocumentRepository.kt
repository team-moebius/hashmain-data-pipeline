package com.moebius.tracker.domain.trades

import com.moebius.tracker.domain.ElasticDocumentRepository
import java.time.LocalDateTime

interface TradeDocumentRepository : ElasticDocumentRepository<TradeDocument> {
    fun getByDateTimeRange(startDateTime: LocalDateTime, endDateTime: LocalDateTime): List<TradeDocument>
}