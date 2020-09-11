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
import java.util.List;

@RestController
@RequestMapping("trade-histories")
@RequiredArgsConstructor
public class TradeHistoryController {

    private final TradeAggregationService aggregationService;
    private final TradeHistoryService tradeHistoryService;
    private static final String REQUEST_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm'Z'";


    @ApiOperation(value = "aggregated history")
    @GetMapping("/aggregated/{exchange}/{symbol}")
    public TradeStatsAggregationDto getAggregatedTradeHistory(@PathVariable Exchange exchange, @PathVariable String symbol,
                                                              @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime from,
                                                              @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime to,
                                                              @RequestParam("interval") int interval
    ) {

        TradeAggregationRequest request = TradeAggregationRequest.builder()
                .exchange(exchange)
                .symbol(symbol)
                .from(from)
                .to(to)
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

