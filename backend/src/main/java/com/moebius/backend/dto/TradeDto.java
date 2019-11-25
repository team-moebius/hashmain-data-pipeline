package com.moebius.backend.dto;

import com.moebius.backend.domain.commons.Change;
import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.commons.TradeType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class TradeDto {
	private String id;
	private Exchange exchange;
	private String symbol;
	private TradeType tradeType;
	private Change change;
	private double price;
	private double volume;
	private double prevClosingPrice;
	private double changePrice;
	private LocalDateTime createdAt;
}
