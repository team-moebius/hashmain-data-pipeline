package com.moebius.api.mapper;

import com.moebius.api.dto.TradeAggregationRequest;
import com.moebius.api.dto.TradeStatsAggregationDto;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class TradeStatsAggregationBucketMapper {
    public TradeStatsAggregationDto map(com.moebius.api.entity.TradeStatsAggregation aggregation, TradeAggregationRequest request) {
        final var zoneId = request.getFrom().getZone();
        ZonedDateTime zonedDateTime = aggregation.getTimeKey().withZoneSameInstant(zoneId);
        var endTime = zonedDateTime.plusMinutes(request.getInterval());
        if (endTime.toEpochSecond() > request.getTo().toEpochSecond()) {
            endTime = request.getTo();
        }
        return TradeStatsAggregationDto.builder()
                .startTime(zonedDateTime)
                .endTime(endTime)
                .totalAskCount(Double.valueOf(aggregation.getTotalAskCount()).longValue())
                .totalBidCount(Double.valueOf(aggregation.getTotalBidCount()).longValue())
                .totalTransactionCount(Double.valueOf(aggregation.getTotalTransactionCount()).longValue())
                .totalAskPrice(aggregation.getTotalAskPrice())
                .totalAskVolume(aggregation.getTotalAskVolume())
                .totalBidPrice(aggregation.getTotalBidPrice())
                .totalBidVolume(aggregation.getTotalBidVolume())
                .totalTransactionPrice(aggregation.getTotalTransactionPrice())
                .totalTransactionVolume(aggregation.getTotalTransactionVolume())
                .build();
    }
}
