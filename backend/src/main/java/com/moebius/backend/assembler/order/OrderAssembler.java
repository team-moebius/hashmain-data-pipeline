package com.moebius.backend.assembler.order;

import com.moebius.backend.domain.apikeys.ApiKey;
import com.moebius.backend.domain.commons.EventType;
import com.moebius.backend.domain.orders.Order;
import com.moebius.backend.domain.orders.OrderStatus;
import com.moebius.backend.dto.OrderDto;
import com.moebius.backend.dto.frontend.response.OrderResponseDto;
import com.moebius.backend.dto.frontend.response.OrderStatusDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderAssembler {
	private OrderUtil orderUtil;

	public Order toOrderWhenCreate(ApiKey apiKey, OrderDto dto) {
		Order order = new Order();
		order.setApiKeyId(apiKey.getId());
		order.setExchange(dto.getExchange());
		order.setSymbol(dto.getSymbol());
		order.setOrderType(dto.getOrderType());
		order.setOrderStatus(OrderStatus.READY);
		order.setOrderPosition(dto.getOrderPosition());
		order.setPrice(dto.getPrice());
		order.setVolume(dto.getVolume());
		order.setLevel(dto.getLevel());
		order.setCreatedAt(LocalDateTime.now());
		order.setUpdatedAt(LocalDateTime.now());

		return order;
	}

	public Order toOrderWhenUpdate(Order order, OrderDto dto) {
		order.setOrderType(dto.getOrderType());
		order.setPrice(dto.getPrice());
		order.setVolume(dto.getVolume());
		order.setLevel(dto.getLevel());
		order.setUpdatedAt(LocalDateTime.now());

		return order;
	}

	public OrderDto toDto(Order order, EventType eventType) {
		OrderDto orderDto = new OrderDto();
		if (ObjectUtils.allNotNull(order.getId())) {
			orderDto.setId(order.getId().toHexString());
		}
		orderDto.setEventType(eventType);
		orderDto.setExchange(order.getExchange());
		orderDto.setSymbol(order.getSymbol());
		orderDto.setOrderType(order.getOrderType());
		orderDto.setOrderStatus(order.getOrderStatus());
		orderDto.setOrderPosition(order.getOrderPosition());
		orderDto.setPrice(order.getPrice());
		orderDto.setVolume(order.getVolume());
		orderDto.setLevel(order.getLevel());

		return orderDto;
	}

	public OrderResponseDto toResponseDto(List<OrderDto> orders) {
		OrderResponseDto orderResponseDto = new OrderResponseDto();
		orderResponseDto.setOrders(orders);

		return orderResponseDto;
	}

	public OrderStatusDto toStatusDto(OrderDto orderDto) {
		return OrderStatusDto.builder()
			.currency(orderUtil.getCurrencyBySymbol(orderDto.getSymbol()))
			...
			.build();
	}
}
