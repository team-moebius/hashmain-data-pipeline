package com.moebius.api.dto;

import com.moebius.api.entity.TradeHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TradeHistoryDto {

    private String id;

    private String exchange;

    private String symbol;

    private String tradeType;

    private String change;

    private Double price;

    private Double volume;

    private Double prevClosingPrice;

    private Double changePrice;

    private LocalDateTime createdAt;

    public static TradeHistoryDto fromEntity(TradeHistory entity) {
        return new TradeHistoryDto(entity.getId(),
                entity.getExchange(),
                entity.getSymbol(),
                entity.getTradeType(),
                entity.getChange(),
                entity.getPrice(),
                entity.getVolume(),
                entity.getPrevClosingPrice(),
                entity.getChangePrice(),
                entity.getCreatedAt());
    }
}