package com.moebius.api.controller;

import com.moebius.api.dto.TradeAggregationRequest;
import com.moebius.api.dto.TradeHistoryDto;
import com.moebius.api.dto.TradeStatsAggregationDto;
import com.moebius.api.service.TradeAggregationService;
import com.moebius.api.service.TradeHistoryService;
import com.moebius.data.type.Exchange;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("trade-histories")
@RequiredArgsConstructor
public class TradeHistoryController {

    private final TradeAggregationService aggregationService;
    private final TradeHistoryService tradeHistoryService;

    @ApiOperation(value = "aggregated history")
    @GetMapping("/aggregated/{exchange}/{symbol}")
    public TradeStatsAggregationDto getAggregatedTradeHistory(@PathVariable Exchange exchange, @PathVariable String symbol,
                                                              @RequestParam("from") String from,
                                                              @RequestParam("to") String to,
                                                              @RequestParam("interval") int interval
    ) {

        TradeAggregationRequest request = TradeAggregationRequest.builder()
                .exchange(exchange)
                .symbol(symbol)
                .from(ZonedDateTime.parse(from, DateTimeFormatter.ISO_DATE_TIME))
                .to(ZonedDateTime.parse(to, DateTimeFormatter.ISO_DATE_TIME))
                .interval(interval)
                .build();
        return aggregationService.getTradeStatsAggregation(request);
    }

    @ApiOperation(value = "get histories")
    @GetMapping("/{exchange}/{symbol}")
    public List<TradeHistoryDto> getTradeHistories(@PathVariable Exchange exchange, @PathVariable String symbol,
                                                   @RequestParam(required = false, defaultValue = "100") Integer count) {

        return tradeHistoryService.getLatestHistory(exchange, symbol, count);
    }
}

