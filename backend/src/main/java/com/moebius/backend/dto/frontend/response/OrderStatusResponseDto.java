package com.moebius.backend.dto.frontend.response;

import com.moebius.backend.dto.OrderStatusDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class OrderStatusResponseDto {
	private List<OrderStatusDto> orderStatuses;
}
