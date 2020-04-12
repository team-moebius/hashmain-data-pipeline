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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public Map<String, List<OrderDto>> toCurrencyOrderDtos(List<OrderDto> orders) {
		Map<String, List<OrderDto>> currencyOrdersMap = new HashMap<>();

		orders.forEach(order ->
			currencyOrdersMap.compute(orderUtil.getCurrencyBySymbol(order.getSymbol()),
				(currency, sameCurrencyOrders) -> {
					if (sameCurrencyOrders == null) {
						List<OrderDto> newCurrencyOrders = new ArrayList<>();
						newCurrencyOrders.add(order);
						return newCurrencyOrders;
					}
					sameCurrencyOrders.add(order);
					return sameCurrencyOrders;
				}));

		return currencyOrdersMap;
	}

	public List<OrderStatusDto> toStatusDtos(Map<String, List<OrderDto>> orders) {
		return OrderStatusDto.builder()
			.currency(orderUtil.getCurrencyBySymbol(orderDto.getSymbol()))
			.averagePurchasePrice()
			.purchaseAmount()
			.tradePrice()
			.evaluatedPrice()
			.profitLossRatio()
			.orderStatus()
			.build();
	}

	public OrderResponseDto toResponseDto(List<OrderDto> orders, List<OrderStatusDto> orderStatuses) {
		return OrderResponseDto.builder()
			.orders(orders)
			.orderStatuses(orderStatuses)
			.build();
	}
}
