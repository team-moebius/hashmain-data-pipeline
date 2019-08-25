package com.moebius.backend.domain.trades

import com.fasterxml.jackson.annotation.JsonValue

enum class Exchange {
    UPBIT,
    BITHUMB,
    BINANCE
}

enum class Symbol constructor(private val value: String) {
    KRW_BTC("KRW-BTC"),
    KRW_ETH("KRW-ETH"),
    KRW_XRP("KRW-XRP"),
    KRW_EOS("KRW-EOS"),
    KRW_LTC("KRW-LTC"),
    KRW_BCH("KRW-BCH"),
    KRW_TRX("KRW-TRX"),
    KRW_XLM("KRW-XLM");

    @JsonValue
    fun value(): String {
        return value
    }

    override fun toString(): String {
        return value
    }
}

enum class TradeType {
    ASK,
    BID
}

enum class Change {
    RISE,
    EVEN,
    FALL
}

