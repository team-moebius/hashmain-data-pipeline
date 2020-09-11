package com.moebius.api.dto;

import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Builder
public class TradeStatsAggregationBucketDto {

    ZonedDateTime startTime;
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
