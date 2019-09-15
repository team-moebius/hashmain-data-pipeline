package com.moebius.backend.domain.trades

import com.fasterxml.jackson.annotation.JsonFormat
import com.moebius.backend.domain.ElasticDocument
import com.moebius.backend.domain.commons.Change
import com.moebius.backend.domain.commons.Exchange
import com.moebius.backend.domain.commons.TradeType
import java.time.Instant
import java.util.*

data class TradeDocument(
        val id: String,
        val exchange: Exchange,
        val symbol: String,
        val tradeType: TradeType,
        val change: Change,
        val price: Double,
        val volume: Double,
        val prevClosingPrice: Double,
        val changePrice: Double,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        val createdAt: Date = Date.from(Instant.now())
) : ElasticDocument {
    override fun getDocumentId(): String = id

    companion object {
        fun of(exchange: Exchange,
               symbol: String,
               tradeType: TradeType,
               change: Change,
               price: Double,
               volume: Double,
               prevClosingPrice: Double,
               changePrice: Double
        ): TradeDocument =
                TradeDocument("${UUID.randomUUID()}", exchange, symbol, tradeType, change, price, volume, prevClosingPrice, changePrice)
    }
}