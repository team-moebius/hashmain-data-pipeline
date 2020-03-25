package com.moebius.backend.dto;

import com.moebius.backend.domain.commons.Exchange;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MarketDto {
	private Exchange exchange;
	private String symbol;
	private double currentPrice;
	private double changeRate;
	private double accumulatedTradePrice;
	private double accumulatedTradeVolume;
}
