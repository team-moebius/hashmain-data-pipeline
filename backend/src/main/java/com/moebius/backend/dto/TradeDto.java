package com.moebius.backend.dto;

import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.commons.TradeType;
import lombok.Data;

@Data
public class TradeDto {
	private Exchange exchange;
	private String symbol;
	private TradeType tradeType;
	private double price;
	private double volume;
}
