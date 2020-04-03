package com.moebius.backend.dto.frontend.response;

import com.moebius.backend.dto.OrderDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderResponseDto {
	private List<OrderDto> orders;
}
