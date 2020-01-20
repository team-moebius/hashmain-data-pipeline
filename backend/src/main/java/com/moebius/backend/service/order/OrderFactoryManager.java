package com.moebius.backend.service.order;

import com.moebius.backend.domain.orders.OrderPosition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OrderFactoryManager {
	private final Map<OrderPosition, OrderFactory> ordersFactoryMap;

	public OrderFactoryManager(List<OrderFactory> ordersFactories) {
		this.ordersFactoryMap = ordersFactories.stream()
			.collect(Collectors.toMap(OrderFactory::getPosition, Function.identity()));
	}

	public OrderFactory getOrdersFactory(OrderPosition orderPosition) {
		return ordersFactoryMap.get(orderPosition);
	}
}
