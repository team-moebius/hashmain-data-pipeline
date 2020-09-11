package com.moebius.api.dto;

import com.moebius.data.type.Exchange;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class TradeAggregationRequest {
    Exchange exchange;
    String symbol;
    int minutesAgo;
    LocalDateTime from;
    LocalDateTime to;

    public static class TradeAggregationRequestBuilder{
        public TradeAggregationRequestBuilder minutesAgo(int minutesAgo){
            this.to = LocalDateTime.now();
            this.from = this.to.minusMinutes(minutesAgo);
            return this;
        }
    }
}
