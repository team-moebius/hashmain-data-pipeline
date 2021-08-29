package com.moebius.api.service

import com.moebius.api.dto.TradeAggregationRequest
import com.moebius.api.dto.TradeStatsAggregationDto
import com.moebius.api.entity.TradeStatsAggregation
import com.moebius.api.mapper.TradeStatsAggregationBucketMapper
import com.moebius.api.mapper.TradeStatsAggregationDtoMapper
import com.moebius.api.repository.TradeStatsAggregationRepository
import com.moebius.data.type.Exchange
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.concurrent.CompletableFuture

class TradeAggregationServiceTest extends Specification {

    def mapper = new TradeStatsAggregationDtoMapper(
            new TradeStatsAggregationBucketMapper()
    );

    def repository = Mock(TradeStatsAggregationRepository)

    def subject = new TradeAggregationService(
            repository,
            mapper
    )

    def "GetTradeStatsAggregation"() {
        setup:
        def startTime = LocalDateTime.of(2020, 9, 1, 0, 0, 0)
        def response = new TradeStatsAggregation.TradeStatsAggregationBuilder()
                .timeKey(getZonedTime(startTime, 1))
                .build()
        def request = new TradeAggregationRequest(
                Exchange.UPBIT,
                "symbol", 0,
                ZonedDateTime.of(startTime, ZoneId.systemDefault()),
                ZonedDateTime.of(startTime.plusMinutes(2).withSecond(1), ZoneId.systemDefault()),
                1
        )
        repository.asyncFindTradeStatsAggregation(request) >> {
            return CompletableFuture.completedFuture([response] as List<TradeStatsAggregation>)
        }

        when:
        def result = subject.getTradeStatsAggregation(request)
        then:
        result != null
        result.get().getAggregatedTradeHistories().size() > 1
        result.get().getAggregatedTradeHistories() == [
                TradeStatsAggregationDto.builder().startTime(getZonedTime(startTime, 0)).endTime(getZonedTime(startTime, 1)).build(),
                TradeStatsAggregationDto.builder().startTime(getZonedTime(startTime, 1)).endTime(getZonedTime(startTime, 2)).build()
        ]
    }

    def getZonedTime(LocalDateTime startTime, int plusMinutes) {
        ZonedDateTime.of(startTime.plusMinutes(plusMinutes), ZoneId.systemDefault())
    }
}
