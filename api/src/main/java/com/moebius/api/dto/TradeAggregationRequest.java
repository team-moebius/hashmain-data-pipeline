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

        public TradeAggregationRequestBuilder from(ZonedDateTime from) {
            this.from = TimeUtils.getRoundUpTime(from);
            return this;
        }
        public TradeAggregationRequestBuilder to(ZonedDateTime to){
            if(to.compareTo(ZonedDateTime.now()) > 0){
                this.to = TimeUtils.getRoundUpTime(ZonedDateTime.now(to.getZone()));
            }else{
                this.to = TimeUtils.getRoundUpTime(to);
            }
            return this;
        }
    }
}
