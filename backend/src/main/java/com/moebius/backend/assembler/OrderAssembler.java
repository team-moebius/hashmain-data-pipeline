package com.moebius.backend.assembler;

import com.moebius.backend.domain.apikeys.ApiKey;
import com.moebius.backend.domain.commons.EventType;
import com.moebius.backend.domain.orders.Order;
import com.moebius.backend.dto.frontend.OrderDto;
import com.moebius.backend.dto.frontend.response.OrderResponseDto;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
		order.setOrderPosition(dto.getOrderPosition());
		order.setPrice(dto.getPrice());
		order.setVolume(dto.getVolume());
		if (StringUtils.isBlank(dto.getId()) && dto.getEventType() == EventType.CREATE) {
			order.setCreatedAt(LocalDateTime.now());
		}
		order.setUpdatedAt(LocalDateTime.now());

		return order;
	}

	public OrderResponseDto toResponseDto(@NotNull Order order, EventType eventType) {
		OrderResponseDto responseDto = new OrderResponseDto();
		responseDto.setId(order.getId().toHexString());
		responseDto.setEventType(eventType);
		responseDto.setExchange(order.getExchange());
		responseDto.setSymbol(order.getSymbol());
		responseDto.setOrderType(order.getOrderType());
		responseDto.setOrderPosition(order.getOrderPosition());
		responseDto.setPrice(order.getPrice());
		responseDto.setVolume(order.getVolume());

		return responseDto;
	}

	public OrderResponseDto toSimpleResponseDto(@NotBlank String id, EventType eventType) {
		OrderResponseDto responseDto = new OrderResponseDto();
		responseDto.setId(id);
		responseDto.setEventType(eventType);

		return responseDto;
	}
}
