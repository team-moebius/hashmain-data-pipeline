package com.moebius.backend.service.order.validator;

import com.moebius.backend.domain.orders.OrderPosition;
import com.moebius.backend.dto.OrderDto;
import com.moebius.backend.exception.ExceptionTypes;
import com.moebius.backend.exception.WrongDataException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class OrderValidator {
	public void validate(List<OrderDto> orderDtos) {
		orderDtos.sort(Comparator.comparing(OrderDto::getLevel));

		if (CollectionUtils.isEmpty(orderDtos)) {
			throw new WrongDataException(ExceptionTypes.WRONG_DATA.getMessage(
				"[Order] There isn't order."));
		}

		if (isNotValidPurchase(filterByOrderPosition(orderDtos, OrderPosition.PURCHASE))) {
			throw new WrongDataException(ExceptionTypes.WRONG_DATA.getMessage(
				"[Order] There are wrong level and price in PURCHASE. The HIGHER LEVEL is, The LOWER PRICE should be."));
		}

		if (isNotValidSale(filterByOrderPosition(orderDtos, OrderPosition.SALE))) {
			throw new WrongDataException(ExceptionTypes.WRONG_DATA.getMessage(
				"[Order] There are wrong level and price in SALE. The HIGHER LEVEL is, The HIGHER PRICE should be."));
		}

		if (isNotValidStoploss(filterByOrderPosition(orderDtos, OrderPosition.STOPLOSS))) {
			throw new WrongDataException(ExceptionTypes.WRONG_DATA.getMessage(
				"[Order] There are wrong level and price in STOPLOSS. The HIGHER LEVEL is, The LOWER PRICE should be."));
		}
	}

	private List<OrderDto> filterByOrderPosition(List<OrderDto> orderDtos, OrderPosition orderPosition) {
		return orderDtos.stream()
			.filter(orderDto -> orderDto.getOrderPosition() == orderPosition)
			.collect(Collectors.toList());
	}

	private boolean isNotValidPurchase(List<OrderDto> orderDtos) {
		return orderDtos.size() > 1
			&& IntStream.range(0, orderDtos.size() - 1)
			.anyMatch(index -> orderDtos.get(index).getPrice() < orderDtos.get(index + 1).getPrice());
	}

	private boolean isNotValidSale(List<OrderDto> orderDtos) {
		return orderDtos.size() > 1
			&& IntStream.range(0, orderDtos.size() - 1)
			.anyMatch(index -> orderDtos.get(index).getPrice() > orderDtos.get(index + 1).getPrice());
	}

	private boolean isNotValidStoploss(List<OrderDto> orderDtos) {
		return orderDtos.size() > 1
			&& IntStream.range(0, orderDtos.size() - 1)
			.anyMatch(index -> orderDtos.get(index).getPrice() < orderDtos.get(index + 1).getPrice());
	}
}
