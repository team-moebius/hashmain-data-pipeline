package com.moebius.backend.service.order;

import com.moebius.backend.domain.orders.OrderPosition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OrdersFactoryManager {
	private final Map<OrderPosition, OrdersFactory> ordersFactoryMap;

	public OrdersFactoryManager(List<OrdersFactory> ordersFactories) {
		this.ordersFactoryMap = ordersFactories.stream()
			.collect(Collectors.toMap(OrdersFactory::getPosition, Function.identity()));
	}

	public OrdersFactory getOrdersFactory(OrderPosition orderPosition) {
		return ordersFactoryMap.get(orderPosition);
	}
}
