package com.moebius.backend.dto.frontend.response;

import com.moebius.backend.domain.commons.EventType;
import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.orders.OrderPosition;
import com.moebius.backend.domain.orders.OrderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@ToString
public class OrderResponseDto {
	@Id
	private String id;
	@NotNull
	private EventType eventType;
	private Exchange exchange;
	private String symbol;
	private OrderType orderType;
	private OrderPosition orderPosition;
	@PositiveOrZero
	private double price;
	@PositiveOrZero
	private double volume;
}
