package com.moebius.api.service;

import com.moebius.api.dto.TradeAggregationRequest;
import com.moebius.api.dto.TradeStatsAggregationDto;
import com.moebius.api.mapper.TradeStatsAggregationDtoMapper;
import com.moebius.api.repository.TradeStatsAggregationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeAggregationService {
    private final TradeStatsAggregationRepository repository;
    private final TradeStatsAggregationDtoMapper aggregationDtoMapper;

    public TradeStatsAggregationDto getTradeStatsAggregation(TradeAggregationRequest request) {
        var aggregations = repository.findTradeStatsAggregation(request);
        return aggregationDtoMapper.map(aggregations, request);
    }
}