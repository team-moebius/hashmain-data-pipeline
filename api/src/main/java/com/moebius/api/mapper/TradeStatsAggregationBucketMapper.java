package com.moebius.api.mapper;

import com.moebius.api.dto.TradeStatsAggregationBucketDto;
import com.moebius.api.entity.TradeStatsAggregation;
import org.springframework.stereotype.Component;

@Component
public class TradeStatsAggregationBucketMapper {
    public TradeStatsAggregationBucketDto map(TradeStatsAggregation aggregation, int interval) {
        return TradeStatsAggregationBucketDto.builder()
                .startTime(aggregation.getTimeKey())
                .endTime(aggregation.getTimeKey().plusMinutes(interval))
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