package com.moebius.backend.domain.trades

import com.moebius.backend.domain.ElasticDocumentRepository
import java.time.LocalDateTime

interface TradeDocumentRepository : ElasticDocumentRepository<TradeDocument> {
    fun getByDateTimeRange(startDateTime: LocalDateTime, endDateTime: LocalDateTime): List<TradeDocument>
}