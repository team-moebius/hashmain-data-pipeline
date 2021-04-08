package com.moebius.api.dto;

import com.moebius.data.type.Exchange;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class TradeStatsAggregationResponse {

    Exchange exchange;
    String symbol;
    int interval;

    List<TradeStatsAggregationDto> aggregatedTradeHistories;
}
