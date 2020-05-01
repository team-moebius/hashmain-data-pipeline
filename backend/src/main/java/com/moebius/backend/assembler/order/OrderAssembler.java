package com.moebius.backend.assembler.order;

import com.moebius.backend.domain.apikeys.ApiKey;
import com.moebius.backend.domain.commons.EventType;
import com.moebius.backend.domain.orders.Order;
import com.moebius.backend.domain.orders.OrderStatus;
import com.moebius.backend.domain.orders.OrderStatusCondition;
import com.moebius.backend.dto.OrderDto;
import com.moebius.backend.dto.OrderAssetDto;
import com.moebius.backend.dto.OrderStatusDto;
import com.moebius.backend.dto.TradeDto;
import com.moebius.backend.dto.exchange.AssetDto;
import com.moebius.backend.dto.frontend.response.OrderResponseDto;
import com.moebius.backend.dto.frontend.response.OrderAssetResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.math3.util.Precision;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderAssembler {
	private final OrderUtil orderUtil;

	public Order assembleOrderWhenCreate(ApiKey apiKey, OrderDto dto) {
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

	public Order assembleOrderWhenUpdate(Order order, OrderDto dto) {
		order.setOrderType(dto.getOrderType());
		order.setPrice(dto.getPrice());
		order.setVolume(dto.getVolume());
		order.setLevel(dto.getLevel());
		order.setUpdatedAt(LocalDateTime.now());

		return order;
	}

	public Order assembleUpdatedStatusOrder(Order order, OrderStatusDto orderStatusDto) {
		order.setOrderStatus(orderStatusDto.getOrderStatus());
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
		return OrderResponseDto.builder()
			.orders(orders)
			.build();
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

	public OrderAssetDto toOrderAssetDto(List<OrderDto> orders, AssetDto asset, double currentPrice) {
		if (asset == null || currentPrice == 0D) {
			return OrderAssetDto.builder()
				.currency(orderUtil.getCurrencyBySymbol(orders.get(0).getSymbol()))
				.orderStatus(identifyOrderStatus(orders))
				.build();
		}
		return OrderAssetDto.builder()
			.currency(orderUtil.getCurrencyBySymbol(orders.get(0).getSymbol()))
			.averagePurchasePrice(asset.getAveragePurchasePrice())
			.balance(asset.getBalance())
			.tradePrice(asset.getAveragePurchasePrice() * asset.getBalance())
			.evaluatedPrice(currentPrice * asset.getBalance())
			.profitLossRatio(Precision.round(currentPrice / asset.getAveragePurchasePrice() - 1, 4) * 100)
			.orderStatus(identifyOrderStatus(orders))
			.build();

	}

	public OrderAssetResponseDto toStatusResponseDto(List<OrderAssetDto> orderStatuses) {
		return OrderAssetResponseDto.builder()
			.orderStatuses(orderStatuses)
			.build();
	}

	public OrderStatusCondition assembleInProgressStatusCondition(TradeDto tradeDto) {
		return OrderStatusCondition.builder()
			.exchange(tradeDto.getExchange())
			.symbol(tradeDto.getSymbol())
			.orderStatus(OrderStatus.IN_PROGRESS)
			.build();
	}

	private OrderStatus identifyOrderStatus(List<OrderDto> orders) {
		return hasInProgressStatus(orders) ? OrderStatus.IN_PROGRESS : OrderStatus.READY;
	}

	private boolean hasInProgressStatus(List<OrderDto> orders) {
		return orders.stream().anyMatch(order -> order.getOrderStatus() == OrderStatus.IN_PROGRESS);
	}
}
