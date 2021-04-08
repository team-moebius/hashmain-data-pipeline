package com.moebius.api.consumer.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.moebius.data.type.Exchange;
import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = TradeStats.Builder.class)
public class TradeStats {

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder { }

    String id;
    Exchange exchange;
    String symbol;
    String timeUnit;

    @lombok.Builder.Default
    Long totalAskCount = 0L;

    @lombok.Builder.Default
    Double totalAskPrice = 0.0;

    @lombok.Builder.Default
    Double totalAskVolume = 0.0;

    @lombok.Builder.Default
    Long totalBidCount = 0L;

    @lombok.Builder.Default
    Double totalBidPrice = 0.0;

    @lombok.Builder.Default
    Double totalBidVolume = 0.0;

    @lombok.Builder.Default
    Long totalTransactionCount = 0L;
    @lombok.Builder.Default
    Double totalTransactionPrice = 0.0;
    @lombok.Builder.Default
    Double totalTransactionVolume = 0.0;

    ZonedDateTime statsDate;

    ZonedDateTime startDate;
    ZonedDateTime endDate;


}
