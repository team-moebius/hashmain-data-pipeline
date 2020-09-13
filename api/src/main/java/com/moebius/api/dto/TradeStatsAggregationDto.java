package com.moebius.api.dto;

import com.moebius.data.type.Exchange;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class TradeStatsAggregationDto {

    Exchange exchange;
    String symbol;
    int interval;

    List<TradeStatsAggregationBucketDto> aggregatedTradeHistories;
}
