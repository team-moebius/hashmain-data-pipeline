package com.moebius.backend.dto.frontend.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.moebius.backend.domain.orders.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@JsonDeserialize(builder = OrderStatusDto.OrderStatusDtoBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderStatusDto {
	private String currency;
	private double averagePurchasePrice;
	private double balance;
	private double tradePrice;
	private double evaluatedPrice;
	private double profitLossRatio;
	private OrderStatus orderStatus;
}
