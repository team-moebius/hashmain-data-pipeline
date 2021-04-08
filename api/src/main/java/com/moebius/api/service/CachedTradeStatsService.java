package com.moebius.api.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.google.common.collect.Lists;
import com.moebius.api.consumer.message.TradeStats;
import com.moebius.api.dto.TradeAggregationRequest;
import com.moebius.api.dto.TradeStatsAggregationDto;
import com.moebius.api.dto.TradeStatsAggregationResponse;
import com.moebius.api.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toUnmodifiableList;

@Slf4j
@Service
@RequiredArgsConstructor
public class CachedTradeStatsService {

    public static final Function<List<TradeStats>, TradeStatsAggregationDto> aggregation = o -> {
        var minTime = o.stream().min(Comparator.comparing(TradeStats::getStatsDate)).get().getStatsDate();
        var maxTime = o.stream().max(Comparator.comparing(TradeStats::getStatsDate)).get().getStatsDate().plusMinutes(1L);
        return TradeStatsAggregationDto.builder()
                .startTime(minTime)
                .endTime(maxTime)
                .totalAskCount(o.stream().mapToLong(TradeStats::getTotalAskCount).sum())
                .totalBidCount(o.stream().mapToLong(TradeStats::getTotalBidCount).sum())
                .totalTransactionCount(o.stream().mapToLong(TradeStats::getTotalTransactionCount).sum())
                .totalAskPrice(o.stream().mapToDouble(TradeStats::getTotalAskPrice).sum())
                .totalAskVolume(o.stream().mapToDouble(TradeStats::getTotalAskVolume).sum())
                .totalBidPrice(o.stream().mapToDouble(TradeStats::getTotalBidPrice).sum())
                .totalBidVolume(o.stream().mapToDouble(TradeStats::getTotalBidVolume).sum())
                .totalTransactionPrice(o.stream().mapToDouble(TradeStats::getTotalTransactionPrice).sum())
                .totalTransactionVolume(o.stream().mapToDouble(TradeStats::getTotalTransactionVolume).sum())
                .build();
    };

    private final Cache<String, TradeStats> tradeStatsCache;

    public TradeStatsAggregationResponse getAggregateTradeStats(TradeAggregationRequest request) {
        return TradeStatsAggregationResponse.builder()
                .interval(request.getInterval())
                .exchange(request.getExchange())
                .symbol(request.getSymbol())
                .aggregatedTradeHistories(getTradeStats(request).stream().collect(Collectors.toUnmodifiableList()))
                .build();
    }


    private Collection<TradeStatsAggregationDto> getTradeStats(TradeAggregationRequest request) {
        var from = request.getRoundUpFrom();
        var to = request.getRoundDownTo();
        var diff = (to.toEpochSecond() - from.toEpochSecond()) / TimeUnit.MINUTES.toSeconds(1);

        var values = IntStream.rangeClosed(0, (int) diff)
                .mapToLong(o -> from.toEpochSecond() + TimeUnit.MINUTES.toSeconds(o))
                .mapToObj(time -> {
                    var key = String.format("%s-%s-1m-%s", request.getExchange(), request.getSymbol(), time);
                    return Optional.ofNullable(tradeStatsCache.getIfPresent(key))
                            .orElse(TradeStats.builder()
                                    .exchange(request.getExchange())
                                    .symbol(request.getSymbol())
                                    .statsDate(Instant.ofEpochSecond(time).atZone(request.getFrom().getZone()))
                                    .build());
                })
                .collect(toUnmodifiableList());

        return Lists.partition(values, request.getInterval()).stream().map(aggregation).collect(toUnmodifiableList());
    }

    public boolean isCachedRange(ZonedDateTime from, ZonedDateTime to) {
        var now = ZonedDateTime.now();
        var oneHourBefore = now.minusHours(1);
        return from.isAfter(oneHourBefore) && to.isBefore(now);
    }
}
