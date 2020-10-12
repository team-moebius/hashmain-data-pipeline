package com.moebius.api.dto;

import com.moebius.api.util.TimeUtils;
import com.moebius.data.type.Exchange;
import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Builder
public class TradeAggregationRequest {
    Exchange exchange;
    String symbol;
    int minutesAgo;
    ZonedDateTime from;
    ZonedDateTime to;
    int interval;

    public static class TradeAggregationRequestBuilder {
        public TradeAggregationRequestBuilder minutesAgo(int minutesAgo) {
            this.to = ZonedDateTime.now();
            this.from = this.to.minusMinutes(minutesAgo);
            return this;
        }
    }

    public ZonedDateTime getRoundUpFrom() {
        return TimeUtils.getRoundUpTime(getFrom());
    }

    public ZonedDateTime getRoundDownTo() {
        return TimeUtils.getRoundDownTime(getTo());
    }
}
