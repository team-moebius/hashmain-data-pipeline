package com.moebius.api.controller;

import com.moebius.api.dto.TradeAggregationDto;
import com.moebius.api.dto.TradeHistoryDto;
import com.moebius.api.service.TradeAggregationService;
import com.moebius.api.service.TradeHistoryService;
import com.moebius.data.type.Exchange;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("trade-histories")
@RequiredArgsConstructor
public class TradeHistoryController {

    private final TradeAggregationService aggregationService;
    private final TradeHistoryService tradeHistoryService;

    @GetMapping("/aggregated/{exchange}/{symbol}")
    public TradeAggregationDto getAggregatedTradeHistory(@PathVariable Exchange exchange, @PathVariable String symbol,
                                                         @RequestParam(required = false, defaultValue = "10") Integer minutesAgo) {
        return aggregationService.getTradeAggregation(exchange, symbol, minutesAgo);
    }

    @GetMapping("/{exchange}/{symbol}")
    public List<TradeHistoryDto> getTradeHistories(@PathVariable Exchange exchange, @PathVariable String symbol,
                                                   @RequestParam(required = false, defaultValue = "100") Integer count) {

        return tradeHistoryService.getLatestHistory(exchange, symbol, count);
    }
}
