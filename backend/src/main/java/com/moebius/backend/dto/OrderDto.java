package com.moebius.backend.dto;

import com.moebius.backend.domain.commons.EventType;
import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.orders.OrderPosition;
import com.moebius.backend.domain.orders.OrderStatus;
import com.moebius.backend.domain.orders.OrderType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@ToString
public class OrderDto {
	@Id
	@ApiModelProperty(notes = "Order Id, 이미 생성된 order일 경우 삭제 또는 수정 시 반드시 넣어서 보내줘야 한다.")
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
	private OrderStatus orderStatus;
	@NotNull
	private OrderPosition orderPosition;
	@PositiveOrZero
	private double price;
	@PositiveOrZero
	private double volume;
	@PositiveOrZero
	private int level;
}
