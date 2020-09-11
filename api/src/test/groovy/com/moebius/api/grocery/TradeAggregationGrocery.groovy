package com.moebius.api.grocery

import com.moebius.api.entity.TradeAggregation

class TradeAggregationGrocery {
    static def basicTradeAgg(Map map = defaultMap()) {
        return new TradeAggregation(
                map.id as String,
                map.exchange as String,
                map.symbol as String,
                map.timeUnit as String,
                map.totalAskCount as Long,
                map.totalAskPrice as Double,
                map.totalAskVolume as Double,
                map.totalBidCount as Long,
                map.totalBidPrice as Double,
                map.totalBidVolume as Double,
                map.totalTransactionCount as Long,
                map.totalTransactionPrice as Double,
                map.totalTransactionVolume as Double,
                map.statsDate as String
        )
    }

    static def defaultMap() {
        return ["totalAskPrice"         : 0.0,
                "statsDate"             : "2020-05-03T10:06",
                "id"                    : "UPBITKRW-SC26474466",
                "totalBidCount"         : 1,
                "totalAskVolume"        : 0.0,
                "totalBidVolume"        : 70272.6127711,
                "exchange"              : "UPBIT",
                "symbol"                : "KRW-SC",
                "totalAskCount"         : 0,
                "totalBidPrice"         : 190438.780609681,
                "totalTransactionVolume": 70272.6127711,
                "totalTransactionCount" : 1,
                "timeUnit"              : "1m",
                "totalTransactionPrice" : 190438.780609681]
    }
}
