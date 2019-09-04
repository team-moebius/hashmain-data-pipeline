package com.moebius.backend.domain.trades

import com.moebius.backend.domain.commons.Exchange
import com.moebius.backend.domain.commons.Symbol
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval
import java.time.LocalDateTime

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
        val timeUnit: DateHistogramInterval,
        val totalAskCount: Int,
        val totalAskPrice: Double,
        val totalBidCount: Int,
        val totalBidPrice: Double,
        val totalTransactionCount: Int,
        val totalTransactionPrice: Double,
        val createdAt: LocalDateTime
)