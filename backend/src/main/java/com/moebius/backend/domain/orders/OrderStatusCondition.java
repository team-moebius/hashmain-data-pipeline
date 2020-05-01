package com.moebius.backend.domain.orders;

import com.moebius.backend.domain.commons.Exchange;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OrderStatusCondition {
	private Exchange exchange;
	private String symbol;
	private OrderStatus orderStatus;
}
