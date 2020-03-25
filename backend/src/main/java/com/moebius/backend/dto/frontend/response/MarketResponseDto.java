package com.moebius.backend.dto.frontend.response;

import com.moebius.backend.domain.commons.Exchange;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class MarketResponseDto {
	private Exchange exchange;
	private String symbol;
	private double currentPrice;
	private double changeRate;
	private double accumulatedTradePrice;
}
