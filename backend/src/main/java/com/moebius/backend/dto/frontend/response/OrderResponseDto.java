package com.moebius.backend.dto.frontend.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.moebius.backend.dto.OrderDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@JsonDeserialize(builder = OrderResponseDto.OrderResponseDtoBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponseDto {
	private List<OrderDto> orders;
	private List<OrderStatusDto> orderStatuses;
}
