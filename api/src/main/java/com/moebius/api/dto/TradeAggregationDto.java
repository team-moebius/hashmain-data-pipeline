package com.moebius.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    private LocalDateTime startAt;

    private LocalDateTime endAt;
}
