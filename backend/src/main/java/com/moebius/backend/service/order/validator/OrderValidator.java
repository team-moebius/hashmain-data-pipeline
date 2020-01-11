package com.moebius.backend.service.order.validator;

import com.moebius.backend.domain.orders.OrderPosition;
import com.moebius.backend.dto.OrderDto;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class OrderValidator {
	public boolean isNotValidToSave(List<OrderDto> orderDtos) {
		orderDtos.sort(Comparator.comparing(OrderDto::getLevel));
		return isNotValidPurchase(filterByOrderPosition(orderDtos, OrderPosition.PURCHASE))
			|| isNotValidSale(filterByOrderPosition(orderDtos, OrderPosition.SALE))
			|| isNotValidStoploss(filterByOrderPosition(orderDtos, OrderPosition.STOPLOSS));
	}

	private List<OrderDto> filterByOrderPosition(List<OrderDto> orderDtos, OrderPosition orderPosition) {
		return orderDtos.stream()
			.filter(orderDto -> orderDto.getOrderPosition() == orderPosition)
			.collect(Collectors.toList());
	}

	private boolean isNotValidPurchase(List<OrderDto> orderDtos) {
		return IntStream.range(0, orderDtos.size() - 1)
			.allMatch(index -> orderDtos.get(index).getPrice() > orderDtos.get(index + 1).getPrice());
	}

	private boolean isNotValidSale(List<OrderDto> orderDtos) {
		return IntStream.range(0, orderDtos.size() - 1)
			.allMatch(index -> orderDtos.get(index).getPrice() < orderDtos.get(index + 1).getPrice());
	}

	private boolean isNotValidStoploss(List<OrderDto> orderDtos) {
		return IntStream.range(0, orderDtos.size() - 1)
			.allMatch(index -> orderDtos.get(index).getPrice() > orderDtos.get(index + 1).getPrice());
	}
}
