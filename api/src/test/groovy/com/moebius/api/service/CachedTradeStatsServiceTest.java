package com.moebius.api.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.moebius.api.consumer.message.TradeStats;
import com.moebius.api.dto.TradeAggregationRequest;
import com.moebius.data.type.Exchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class CachedTradeStatsServiceTest {


    private Cache<String, TradeStats> tradeStatsCache = Caffeine.newBuilder().build();

    private CachedTradeStatsService statsService = new CachedTradeStatsService(tradeStatsCache);

    @BeforeEach
    public void init() {
        tradeStatsCache.cleanUp();
    }



    @Test
    public void getAggregateTradeStatsTest() {
        //setup
        var time = ZonedDateTime.of(2021, 1, 2, 12, 0, 0, 0, ZoneId.systemDefault());

        for (int i = 4; i >= 0; i--) {
            tradeStatsCache.put(Exchange.UPBIT+"-symbol-1m-" +
                    time.minusMinutes(i).withSecond(0).toEpochSecond(), TradeStats.builder()
                    .exchange(Exchange.UPBIT)
                    .symbol("symbol")
                    .totalAskCount(1L)
                    .totalAskPrice(1.0)
                    .totalAskVolume(1.0)
                    .totalBidCount(1L)
                    .totalBidPrice(1.0)
                    .totalBidVolume(1.0)
                    .totalTransactionCount(1L)
                    .totalTransactionPrice(1.0)
                    .totalTransactionVolume(1.0)
                    .startDate(time.minusMinutes(i).withSecond(0))
                    .endDate(time.minusMinutes(i).withSecond(59))
                    .statsDate(time.minusMinutes(i).withSecond(0))
                    .build());
        }

        //when
        var a = statsService.getAggregateTradeStats(TradeAggregationRequest.builder()
                .from(time.minusMinutes(4).withSecond(0))
                .to(time.withSecond(0))
                .interval(2)
                .exchange(Exchange.UPBIT)
                .symbol("symbol")
                .build());

        //then
        System.out.println(a);
    }

    @Test
    public void isCachedRangeTest(){
        assertTrue(statsService.isCachedRange(ZonedDateTime.now().minusMinutes(20),
                ZonedDateTime.now().minusMinutes(10)));
        assertFalse(statsService.isCachedRange(ZonedDateTime.now().minusMinutes(70),
                ZonedDateTime.now().minusMinutes(10)));
        assertFalse(statsService.isCachedRange(ZonedDateTime.now().minusMinutes(20),
                ZonedDateTime.now().plusMinutes(1)));
    }
}