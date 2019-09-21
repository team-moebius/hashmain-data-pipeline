package com.moebius.backend.dto.frontend.response;

import com.moebius.backend.domain.commons.EventType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class OrderResponseDto {
	@Id
	private String id;
	@NotNull
	private EventType eventType;
	@NotNull
	private Exchange exchange;
	@NotNull
	private String symbol;
	@NotNull
	private OrderType orderType;
	@NotNull
	private OrderPosition orderPosition;
	@PositiveOrZero
	private double price;
	@PositiveOrZero
	private double volume;
}
