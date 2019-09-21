package com.moebius.backend.assembler;

import com.moebius.backend.domain.apikeys.ApiKey;
import com.moebius.backend.domain.commons.EventType;
import com.moebius.backend.domain.orders.Order;
import com.moebius.backend.dto.frontend.OrderDto;
import com.moebius.backend.dto.frontend.response.OrderResponseDto;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderAssembler {
	public List<Order> toOrders(@NotNull ApiKey apiKey, @NotNull List<OrderDto> orderDtos) {
		List<Order> orders = new ArrayList<>();
		orderDtos.forEach(dto -> {
			Order order = new Order();
			order.setId(dto.getEventType() == EventType.CREATE ? null : new ObjectId(dto.getId()));
			order.setApiKeyId(apiKey.getId());
			order.setExchange(dto.getExchange());
			order.setSymbol(dto.getSymbol());
			order.setOrderType(dto.getOrderType());
			order.setOrderPosition(dto.getOrderPosition());
			order.setPrice(dto.getPrice());
			order.setVolume(dto.getVolume());
			orders.add(order);
		});
		return orders;
	}

	public OrderResponseDto toResponseDto(@NotNull Order order, EventType eventType) {
		OrderResponseDto responseDto = new OrderResponseDto();
		responseDto.setId(order.getId().toHexString());
		responseDto.setEventType(eventType);

		return responseDto;
	}
}