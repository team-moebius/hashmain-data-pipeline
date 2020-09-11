package com.moebius.api.controller;

import com.moebius.api.dto.TradeAggregationDto;
import com.moebius.api.dto.TradeAggregationRequest;
import com.moebius.api.dto.TradeHistoryDto;
import com.moebius.api.service.TradeAggregationService;
import com.moebius.api.service.TradeHistoryService;
import com.moebius.data.type.Exchange;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("trade-histories")
@RequiredArgsConstructor
public class TradeHistoryController {

    private final TradeAggregationService aggregationService;
    private final TradeHistoryService tradeHistoryService;

    @ApiOperation(value = "aggregated history")
    @GetMapping("/aggregated/{exchange}/{symbol}")
    public TradeAggregationDto getAggregatedTradeHistory(@PathVariable Exchange exchange, @PathVariable String symbol,
                                                         @RequestParam(required = false, defaultValue = "10") Integer minutesAgo) {
        TradeAggregationRequest request = TradeAggregationRequest.builder()
                .exchange(exchange)
                .symbol(symbol)
                .minutesAgo(minutesAgo)
                .build();
        return aggregationService.getTradeAggregation(request);
    }

    @ApiOperation(value = "get histories")
    @GetMapping("/{exchange}/{symbol}")
    public List<TradeHistoryDto> getTradeHistories(@PathVariable Exchange exchange, @PathVariable String symbol,
                                                   @RequestParam(required = false, defaultValue = "100") Integer count) {

        return tradeHistoryService.getLatestHistory(exchange, symbol, count);
    }
}
