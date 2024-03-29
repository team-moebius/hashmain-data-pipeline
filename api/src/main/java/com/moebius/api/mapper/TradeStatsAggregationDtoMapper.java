package com.moebius.api.mapper;

import com.moebius.api.dto.TradeAggregationRequest;
import com.moebius.api.dto.TradeStatsAggregationResponse;
import com.moebius.api.entity.TradeStatsAggregation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class TradeStatsAggregationDtoMapper {

    private final TradeStatsAggregationBucketMapper bucketMapper;

    public TradeStatsAggregationResponse map(List<TradeStatsAggregation> aggregation, TradeAggregationRequest request) {
        var buckets = aggregation.stream().map(o -> bucketMapper.map(o, request)).collect(toList());
        return TradeStatsAggregationResponse.builder()
                .aggregatedTradeHistories(buckets)
                .exchange(request.getExchange())
                .interval(request.getInterval())
                .symbol(request.getSymbol())
                .build();
    }
}
