package com.moebius.backend.streams.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TradeDto {
	private String id;
	private String exchange;
	private String symbol;
	private String tradeType;
	private String change;
	private double price;
	private double volume;
	private double prevClosingPrice;
	private double changePrice;
	private long receivedTime;
}
