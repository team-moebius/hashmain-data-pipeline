package com.moebius.backend.dto.frontend;

import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.commons.OrderType;
import com.moebius.backend.domain.commons.TradeType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@ToString
public class StoplossDto {
	@NotNull
	private Exchange exchange;
	@NotNull
	private String symbol;
	@NotNull
	private TradeType tradeType;
	@NotNull
	private OrderType orderType;
	@PositiveOrZero
	private double price;
	@PositiveOrZero
	private double volume;
}
