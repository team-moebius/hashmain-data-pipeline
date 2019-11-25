package com.moebius.tracker.dto.upbit

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.moebius.backend.domain.commons.Change
import com.moebius.backend.domain.commons.Exchange
import com.moebius.backend.domain.commons.TradeType

@JsonIgnoreProperties(ignoreUnknown = true)
data class UpbitTradeDto(
        @JsonProperty("ty")
        val type: String,
        @JsonProperty("cd")
        val symbol: String,
        @JsonProperty("ttms")
        val tradeTimestamp: Long,
        @JsonProperty("tp")
        val tradePrice: Double,
        @JsonProperty("tv")
        val tradeVolume: Double,
        @JsonProperty("ab")
        val askBid: TradeType,
        @JsonProperty("pcp")
        val prevClosingPrice: Double,
        @JsonProperty("c")
        val change: Change,
        @JsonProperty("cp")
        val changePrice: Double,
        @JsonProperty("sid")
        val sequentialId: Long,
        val exchange: Exchange = Exchange.UPBIT
)