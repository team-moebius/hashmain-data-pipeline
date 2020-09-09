package com.moebius.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.moebius.api.derse.TimeZoneStringSerializer;
import com.moebius.data.type.Exchange;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeAggregationDto {

    private long docCount;

    private Exchange exchange;

    private String symbol;

    private long totalAskCount;

    private double totalAskPrice;

    private double totalAskVolume;

    private long totalBidCount;

    private double totalBidPrice;

    private double totalBidVolume;

    private long totalTransactionCount;

    private double totalTransactionPrice;

    private double totalTransactionVolume;

    @JsonSerialize(using = TimeZoneStringSerializer.class)
    private ZonedDateTime startAt;

    @JsonSerialize(using = TimeZoneStringSerializer.class)
    private ZonedDateTime endAt;
}
