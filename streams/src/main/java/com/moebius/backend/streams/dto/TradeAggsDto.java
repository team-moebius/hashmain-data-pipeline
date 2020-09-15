package com.moebius.backend.streams.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Getter
@Setter
public class TradeAggsDto {

    private String id;
    private String exchange;
    private String symbol;
    private String timeUnit;

    private Long totalAskCount;
    private Double totalAskPrice;
    private Double totalAskVolume;

    private Long totalBidCount;
    private Double totalBidPrice;
    private Double totalBidVolume;

    private Long totalTransactionCount;
    private Double totalTransactionPrice;
    private Double totalTransactionVolume;

    private ZonedDateTime statsDate;

    public TradeAggsDto() {
        totalAskCount = 0L;
        totalBidCount = 0L;
        totalTransactionCount = 0L;
        totalAskPrice = 0.0;
        totalAskVolume = 0.0;
        totalBidPrice = 0.0;
        totalBidVolume = 0.0;
        totalTransactionPrice = 0.0;
        totalTransactionVolume = 0.0;
    }

    // TODO if exists other accumulation, extract it
    public TradeAggsDto accumulate(String key, TradeDto tradeDto) {
        if (this.getId() == null) {
            this.setId(key);
            this.setTimeUnit("1m");
            this.setSymbol(tradeDto.getSymbol());
            this.setExchange(tradeDto.getExchange());
            this.setStatsDate(ZonedDateTime.ofInstant(
                    Instant.ofEpochMilli(tradeDto.getReceivedTime()),
                    ZoneOffset.UTC).withNano(0)
                    .withSecond(0));
        }

        double price = tradeDto.getPrice() * tradeDto.getVolume();
        double volume = tradeDto.getVolume();

        if ("ASK".equals(tradeDto.getTradeType())) {
            this.setTotalAskCount(this.getTotalAskCount() + 1);
            this.setTotalAskPrice(this.getTotalAskPrice() + price);
            this.setTotalAskVolume(this.getTotalAskVolume() + volume);
        } else if ("BID".equals(tradeDto.getTradeType())) {
            this.setTotalBidCount(this.getTotalBidCount() + 1);
            this.setTotalBidPrice(this.getTotalBidPrice() + price);
            this.setTotalBidVolume(this.getTotalBidVolume() + volume);
        } else {
            return this;
        }

        this.setTotalTransactionCount(this.getTotalTransactionCount() + 1);
        this.setTotalTransactionPrice(this.getTotalTransactionPrice() + price);
        this.setTotalTransactionVolume(this.getTotalTransactionVolume() + volume);

        return this;
    }
}
