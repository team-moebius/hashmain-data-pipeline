package com.moebius.tracker.dto.upbit

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class UpbitMarketDto(
        val market: String
)