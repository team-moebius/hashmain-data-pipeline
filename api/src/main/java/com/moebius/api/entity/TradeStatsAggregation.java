package com.moebius.api.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.moebius.api.aggregation.FieldAggregationType;
import com.moebius.api.annotation.FieldAnnotation;
import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Builder
@JsonDeserialize(builder = TradeStatsAggregation.TradeStatsAggregationBuilder.class)
public class TradeStatsAggregation {

    ZonedDateTime timeKey;

    long docCount;

    @FieldAnnotation(type = FieldAggregationType.SUM)
    double totalAskCount;

    @FieldAnnotation(type = FieldAggregationType.SUM)
    double totalAskPrice;

    @FieldAnnotation(type = FieldAggregationType.SUM)
    double totalAskVolume;

    @FieldAnnotation(type = FieldAggregationType.SUM)
    double totalBidCount;

    @FieldAnnotation(type = FieldAggregationType.SUM)
    double totalBidPrice;

    @FieldAnnotation(type = FieldAggregationType.SUM)
    double totalBidVolume;

    @FieldAnnotation(type = FieldAggregationType.SUM)
    double totalTransactionCount;

    @FieldAnnotation(type = FieldAggregationType.SUM)
    double totalTransactionPrice;

    @FieldAnnotation(type = FieldAggregationType.SUM)
    double totalTransactionVolume;

    @JsonPOJOBuilder(withPrefix = "")
    public static class TradeStatsAggregationBuilder {
    }
}
