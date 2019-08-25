package com.moebius.backend.domain.trades

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.util.*

data class TradeDocument(
        val id: String,
        val exchange: Exchange,
        val symbol: Symbol,
        val tradeType: TradeType,
        val change: Change,
        val price: Double,
        val volume: Double,
        val prevClosingPrice: Double,
        val changePrice: Double,
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        val createdAt: LocalDateTime = LocalDateTime.now()
) {

    companion object {
        @JvmStatic
        fun of(exchange: Exchange,
               symbol: Symbol,
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