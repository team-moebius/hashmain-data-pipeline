package com.moebius.api.service;

import com.moebius.api.dto.TradeAggregationRequest;
import com.moebius.api.dto.TradeStatsAggregationDto;
import com.moebius.api.mapper.TradeStatsAggregationDtoMapper;
import com.moebius.api.repository.TradeStatsAggregationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TradeAggregationService {
    private final TradeStatsAggregationRepository repository;
    private final TradeStatsAggregationDtoMapper aggregationDtoMapper;

    public CompletableFuture<TradeStatsAggregationDto> getTradeStatsAggregation(TradeAggregationRequest request) {
        return repository.asyncFindTradeStatsAggregation(request).thenApply(
                o -> aggregationDtoMapper.map(o, request)
        );
    }
}