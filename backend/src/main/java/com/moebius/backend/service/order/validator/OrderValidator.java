package com.moebius.backend.service.order.validator;

import com.moebius.backend.dto.OrderDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderValidator {
	public boolean isValidToSave(List<OrderDto> orderDtos) {
		return isValidPurchase(orderDtos)
			&& isValidSale(orderDtos)
			&& isValidStoploss(orderDtos);
	}

	private boolean isValidPurchase(List<OrderDto> orderDtos) {
//		orderDtos.stream().reduce((prevOrderDto, nextOrderDto) -> );
		return true;
	}

	private boolean isValidSale(List<OrderDto> orderDtos) {
		return true;
	}

	private boolean isValidStoploss(List<OrderDto> orderDtos) {
		return true;
	}
}
