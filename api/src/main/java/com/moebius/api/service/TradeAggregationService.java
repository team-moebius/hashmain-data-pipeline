package com.moebius.api.service;

import com.moebius.api.dto.TradeAggregationRequest;
import com.moebius.api.dto.TradeStatsAggregationResponse;
import com.moebius.api.entity.TradeStatsAggregation;
import com.moebius.api.mapper.TradeStatsAggregationDtoMapper;
import com.moebius.api.repository.TradeStatsAggregationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TradeAggregationService {
    private final TradeStatsAggregationRepository repository;
    private final TradeStatsAggregationDtoMapper aggregationDtoMapper;

    public CompletableFuture<TradeStatsAggregationResponse> getTradeStatsAggregation(TradeAggregationRequest request) {
        return repository.asyncFindTradeStatsAggregation(request).thenApply(
                o -> aggregationDtoMapper.map(fillNotExistsTime(o, request), request));
    }

    private List<TradeStatsAggregation> fillNotExistsTime(List<TradeStatsAggregation> list, TradeAggregationRequest request) {
        List<TradeStatsAggregation> result = new ArrayList<>();
        ZonedDateTime startTime = request.getRoundUpFrom();
        ZonedDateTime endTime = request.getRoundDownTo();
        int counter = 0;
        for (; endTime.isAfter(startTime); startTime = startTime.plusMinutes(request.getInterval())) {
            if (list.size() > counter && list.get(counter).getTimeKey().toEpochSecond() - startTime.toEpochSecond() == 0) {
                result.add(list.get(counter++));
            } else {
                result.add(TradeStatsAggregation.builder()
                        .timeKey(startTime)
                        .build());
            }
        }
        return result;
    }
}