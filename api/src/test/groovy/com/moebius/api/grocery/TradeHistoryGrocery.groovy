package com.moebius.api.grocery

import com.moebius.api.entity.TradeHistory

import java.time.LocalDateTime

class TradeHistoryGrocery {
    static def basicTradeHistory(Map map = defaultMap()) {
        return new TradeHistory(
                map.id as String,
                map.exchange as String,
                map.symbol as String,
                map.tradeType as String,
                map.change as String,
                map.price as Double,
                map.volume as Double,
                map.prevClosingPrice as Double,
                map.changePrice as Double,
                map.createdAt as LocalDateTime
        )
    }

    static def defaultMap() {
        return [
                "id"              : "06a4d841-a18e-4a22-b033-dc78a9eada83",
                "exchange"        : "UPBIT",
                "symbol"          : "KRW-EOS",
                "tradeType"       : "BID",
                "change"          : "RISE",
                "price"           : 3135.0,
                "volume"          : 16.7106,
                "prevClosingPrice": 3085.0,
                "changePrice"     : 50.0,
                "transactionPrice": 52387.731,
                "createdAt"       : "2020-07-25T15:00:36.040"
        ]
    }
}
