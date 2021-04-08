package com.moebius.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Builder
public class TradeStatsAggregationDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "Asia/Seoul")
    ZonedDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "Asia/Seoul")
    ZonedDateTime endTime;

    long totalAskCount;
    long totalBidCount;
    long totalTransactionCount;

    double totalAskPrice;
    double totalAskVolume;
    double totalBidPrice;
    double totalBidVolume;
    double totalTransactionPrice;
    double totalTransactionVolume;
}
