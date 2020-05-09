package com.moebius.backend.assembler.order;

import com.moebius.backend.domain.orders.Order;
import com.moebius.backend.domain.orders.OrderPosition;
import com.moebius.backend.domain.orders.OrderType;
import com.moebius.backend.dto.OrderDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderUtil {
	private static String SEPARATOR = "-";
	private static int CURRENCY_INDEX = 1;

	public String getCurrencyBySymbol(String symbol) {
		return symbol.split(SEPARATOR)[CURRENCY_INDEX];
	}

	public boolean isExchangeRequestNeeded(Order order, double price) {
		if (order.getOrderType() == OrderType.MARKET) {
			return true;
		}

		return order.getOrderType() == OrderType.LIMIT && (
			(order.getOrderPosition() == OrderPosition.PURCHASE && order.getPrice() >= price) ||
				(order.getOrderPosition() == OrderPosition.SALE && order.getPrice() <= price) ||
				(order.getOrderPosition() == OrderPosition.STOPLOSS && order.getPrice() >= price));
	}

	public List<OrderDto> filterOrdersBySymbol(List<OrderDto> orderDtos, String symbol) {
		return orderDtos.stream()
			.filter(orderDto -> orderDto.getSymbol().equals(symbol.toUpperCase()))
			.collect(Collectors.toList());
	}
}
