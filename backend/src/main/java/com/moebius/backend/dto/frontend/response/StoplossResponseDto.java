package com.moebius.backend.dto.frontend.response;

import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.commons.OrderType;
import com.moebius.backend.domain.commons.Symbol;
import com.moebius.backend.domain.commons.TradeType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@ToString
public class StoplossResponseDto {
	@Id
	private String id;
	@NotNull
	private Exchange exchange;
	@NotNull
	private Symbol symbol;
	@NotNull
	private TradeType tradeType;
	@NotNull
	private OrderType orderType;
	@PositiveOrZero
	private double price;
	@PositiveOrZero
	private double volume;
}
