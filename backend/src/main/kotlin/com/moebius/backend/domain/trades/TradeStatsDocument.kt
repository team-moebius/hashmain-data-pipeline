package com.moebius.backend.domain.trades

import com.fasterxml.jackson.annotation.JsonFormat
import com.moebius.backend.domain.ElasticDocument
import com.moebius.backend.domain.commons.Exchange
import com.moebius.backend.domain.commons.Symbol
import com.moebius.backend.utils.ElasticUtils
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

data class TradeStatsDocument(
        /**
         * 거래소
         * 종목
         * 단위 시간
         * 전체 매수량
         * 전체 매수 금액
         * 전체 매도량
         * 전체 매도 금액
         * 전체 거래량 ( 매수량 + 매도량)
         * 전체 거래 금액 ( 매수 금액 + 매도 금액 )
         * 생성 일자
         */
        val id: String,
        val exchange: Exchange,
        val symbol: Symbol,
        val timeUnit: ElasticUtils.AggregationInterval,
        val totalAskCount: Long,
        val totalAskPrice: Double,
        val totalBidCount: Long,
        val totalBidPrice: Double,
        val totalTransactionCount: Long,
        val totalTransactionPrice: Double,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
        val statsDate: Date,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        val createdAt: Date = Date.from(Instant.now())
) : ElasticDocument {

    override fun getDocumentId(): String = id

    companion object {
        fun of(exchange: Exchange,
               symbol: Symbol,
               timeUnit: ElasticUtils.AggregationInterval,
               totalAskCount: Long,
               totalAskPrice: Double,
               totalBidCount: Long,
               totalBidPrice: Double,
               totalTransactionCount: Long,
               totalTransactionPrice: Double,
               statsDate: LocalDateTime
        ): TradeStatsDocument {
            val date = Date.from(statsDate.atZone(ZoneId.systemDefault()).toInstant())
            return TradeStatsDocument(
                    "$exchange-$symbol-$timeUnit-${date.time}",
                    exchange,
                    symbol,
                    timeUnit,
                    totalAskCount,
                    totalAskPrice,
                    totalBidCount,
                    totalBidPrice,
                    totalTransactionCount,
                    totalTransactionPrice,
                    date
            )
        }
    }

    override fun hashCode(): Int {
        return Objects.hash(exchange, symbol, timeUnit, statsDate.time)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TradeStatsDocument

        if (id != other.id) return false
        if (exchange != other.exchange) return false
        if (symbol != other.symbol) return false
        if (timeUnit != other.timeUnit) return false
        if (totalAskCount != other.totalAskCount) return false
        if (totalAskPrice != other.totalAskPrice) return false
        if (totalBidCount != other.totalBidCount) return false
        if (totalBidPrice != other.totalBidPrice) return false
        if (totalTransactionCount != other.totalTransactionCount) return false
        if (totalTransactionPrice != other.totalTransactionPrice) return false
        if (statsDate != other.statsDate) return false

        return true
    }

    class Builder {
        private var exchange: Exchange? = null
        private var symbol: Symbol? = null
        private var timeUnit: ElasticUtils.AggregationInterval? = null
        private var totalAskCount: Long = 0
        private var totalAskPrice: Double = 0.0
        private var totalBidCount: Long = 0
        private var totalBidPrice: Double = 0.0
        private var totalTransactionCount: Long = 0
        private var totalTransactionPrice: Double = 0.0
        private var statsDate: LocalDateTime? = null

        fun exchange(exchange: Exchange) = apply { this.exchange = exchange }
        fun symbol(symbol: Symbol) = apply { this.symbol = symbol }
        fun timeUnit(timeUnit: ElasticUtils.AggregationInterval) = apply { this.timeUnit = timeUnit }
        fun totalAskCount(totalAskCount: Long) = apply { this.totalAskCount = totalAskCount }
        fun totalAskPrice(totalAskPrice: Double) = apply { this.totalAskPrice = totalAskPrice }
        fun totalBidCount(totalBidCount: Long) = apply { this.totalBidCount = totalBidCount }
        fun totalBidPrice(totalBidPrice: Double) = apply { this.totalBidPrice = totalBidPrice }
        fun totalTransactionCount(totalTransactionCount: Long) = apply { this.totalTransactionCount = totalTransactionCount }
        fun totalTransactionPrice(totalTransactionPrice: Double) = apply { this.totalTransactionPrice = totalTransactionPrice }
        fun statsDate(statsDate: ZonedDateTime) = apply { this.statsDate = LocalDateTime.ofInstant(statsDate.toInstant(), ZoneId.systemDefault()) }

        fun build() = of(
                this.exchange!!,
                this.symbol!!,
                this.timeUnit!!,
                this.totalAskCount,
                this.totalAskPrice,
                this.totalBidCount,
                this.totalBidPrice,
                this.totalTransactionCount,
                this.totalTransactionPrice,
                this.statsDate!!
        )
    }
}