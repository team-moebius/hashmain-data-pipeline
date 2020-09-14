package com.moebius.api.mapper;

import com.moebius.api.dto.TradeAggregationRequest;
import com.moebius.api.dto.TradeStatsAggregationBucketDto;
import com.moebius.api.entity.TradeStatsAggregation;
import org.springframework.stereotype.Component;

@Component
public class TradeStatsAggregationBucketMapper {
    public TradeStatsAggregationBucketDto map(TradeStatsAggregation aggregation, TradeAggregationRequest request) {
        final var zoneId = request.getFrom().getZone();
        return TradeStatsAggregationBucketDto.builder()
                .startTime(aggregation.getTimeKey().withZoneSameInstant(zoneId))
                .endTime(aggregation.getTimeKey().withZoneSameInstant(zoneId)
                        .plusMinutes(request.getInterval()))
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
