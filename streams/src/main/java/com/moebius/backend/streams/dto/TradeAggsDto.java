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

    private ZonedDateTime startDate;
    private ZonedDateTime endDate;

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
    public TradeAggsDto accumulate(TradeDto tradeDto, String timeUnit) {
        ZonedDateTime tradeTimestamp = ZonedDateTime.ofInstant(Instant.ofEpochMilli(tradeDto.getReceivedTime()), ZoneOffset.UTC);

        if (getTimeUnit() == null) {
            setTimeUnit(timeUnit);
        }
        if (getSymbol() == null) {
            setSymbol(tradeDto.getSymbol());
        }

        if (getExchange() == null) {
            setExchange(tradeDto.getExchange());
        }

        if (getStatsDate() == null) {
            int mod = tradeTimestamp.getSecond() % 10;
            this.setStatsDate(tradeTimestamp.withNano(0).minusSeconds(mod));
        }

        if (this.getId() == null) {
            this.setId(String.format("%s-%s-%d", getSymbol(), getTimeUnit(), getStatsDate().toEpochSecond()));
        }

        if (this.getStartDate() == null || this.getStartDate().isAfter(tradeTimestamp)) {
            this.setStartDate(tradeTimestamp);
        }

        if (this.getEndDate() == null || this.getEndDate().isBefore(tradeTimestamp)) {
            this.setEndDate(tradeTimestamp);
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

    public TradeAggsDto accumulate(TradeAggsDto tradeAggsDto, String timeUnit) {
        if (getId() == null) {
            setId(tradeAggsDto.getId());
        }

        if (getExchange() == null) {
            setExchange(tradeAggsDto.getExchange());
        }

        if (getSymbol() == null) {
            setSymbol(tradeAggsDto.getSymbol());
        }

        this.totalAskCount += tradeAggsDto.getTotalAskCount();
        this.totalAskPrice += tradeAggsDto.getTotalAskPrice();
        this.totalAskVolume += tradeAggsDto.getTotalAskVolume();

        this.totalBidCount += tradeAggsDto.getTotalBidCount();
        this.totalBidPrice += tradeAggsDto.getTotalBidPrice();
        this.totalBidVolume += tradeAggsDto.getTotalBidVolume();

        this.totalTransactionCount += tradeAggsDto.getTotalTransactionCount();
        this.totalTransactionPrice += tradeAggsDto.getTotalTransactionPrice();
        this.totalTransactionVolume += tradeAggsDto.getTotalTransactionVolume();

        if (this.startDate == null || this.startDate.isAfter(tradeAggsDto.getStartDate())) {
            this.setStatsDate(tradeAggsDto.getStatsDate());
        }

        if (this.startDate == null || this.getStartDate().isAfter(tradeAggsDto.getStartDate())) {
            this.setStartDate(tradeAggsDto.getStartDate());
        }

        if (this.endDate == null || this.getEndDate().isBefore(tradeAggsDto.getEndDate())) {
            this.setEndDate(tradeAggsDto.getEndDate());
        }
        return this;
    }
}
