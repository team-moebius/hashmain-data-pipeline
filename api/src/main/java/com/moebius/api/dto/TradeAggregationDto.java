package com.moebius.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.moebius.api.derse.TimeZoneStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TradeAggregationDto {

    private String exchange;

    private String symbol;

    private Long totalAskCount;

    private Double totalAskPrice;

    private Double totalAskVolume;

    private Long totalBidCount;

    private Double totalBidPrice;

    private Double totalBidVolume;

    private Long totalTransactionCount;

    private Double totalTransactionPrice;

    private Double totalTransactionVolume;

    @JsonSerialize(using = TimeZoneStringSerializer.class)
    private ZonedDateTime startAt;

    @JsonSerialize(using = TimeZoneStringSerializer.class)
    private ZonedDateTime endAt;
}
