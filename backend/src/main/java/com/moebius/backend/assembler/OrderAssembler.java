package com.moebius.backend.assembler;

import com.moebius.backend.domain.apikeys.ApiKey;
import com.moebius.backend.domain.commons.EventType;
import com.moebius.backend.domain.orders.Order;
import com.moebius.backend.domain.orders.OrderStatus;
import com.moebius.backend.dto.AssetDto;
import com.moebius.backend.dto.OrderDto;
import com.moebius.backend.dto.frontend.response.OrderResponseDto;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderAssembler {
	public Order toOrder(@NotNull ApiKey apiKey, @NotNull OrderDto dto) {
		Order order = new Order(); // TODO : Test create & update
		if (StringUtils.isNotBlank(dto.getId()) && dto.getEventType() == EventType.UPDATE) {
			order.setId(new ObjectId(dto.getId()));
		}
		order.setApiKeyId(apiKey.getId());
		order.setExchange(dto.getExchange());
		order.setSymbol(dto.getSymbol());
		order.setOrderType(dto.getOrderType());
		order.setOrderStatus(OrderStatus.READY);
		order.setOrderPosition(dto.getOrderPosition());
		order.setPrice(dto.getPrice());
		order.setVolume(dto.getVolume());
		if (StringUtils.isBlank(dto.getId()) && dto.getEventType() == EventType.CREATE) {
			order.setCreatedAt(LocalDateTime.now());
		}
		order.setUpdatedAt(LocalDateTime.now());

		return order;
	}

	public OrderDto toDto(@NotNull Order order, EventType eventType) {
		OrderDto orderDto = new OrderDto();
		if (ObjectUtils.allNotNull(order.getId())) {
			orderDto.setId(order.getId().toHexString());
		}
		orderDto.setEventType(eventType);
		orderDto.setExchange(order.getExchange());
		orderDto.setSymbol(order.getSymbol());
		orderDto.setOrderType(order.getOrderType());
		orderDto.setOrderPosition(order.getOrderPosition());
		orderDto.setPrice(order.getPrice());
		orderDto.setVolume(order.getVolume());

		return orderDto;
	}

	public OrderDto toSimpleDto(@NotBlank String id, EventType eventType) {
		OrderDto orderDto = new OrderDto();
		orderDto.setId(id);
		orderDto.setEventType(eventType);

		return orderDto;
	}

	public OrderResponseDto toResponseDto(List<OrderDto> orders, List<AssetDto> assets) {
		OrderResponseDto orderResponseDto = new OrderResponseDto();
		orderResponseDto.setOrders(orders);
		orderResponseDto.setAssets(assets);

		return orderResponseDto;
	}
}
