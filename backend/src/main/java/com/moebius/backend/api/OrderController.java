package com.moebius.backend.api;

import com.moebius.backend.dto.frontend.OrderDto;
import com.moebius.backend.dto.frontend.response.OrderResponseDto;
import com.moebius.backend.exception.DataNotFoundException;
import com.moebius.backend.exception.DataNotVerifiedException;
import com.moebius.backend.service.order.OrderService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;

	@ApiOperation(
		value = "주문(매수, 매도, 역지정) 생성 / 갱신",
		httpMethod = "POST",
		notes = "트레이더가 원하는 주문 정보를 일괄 생성 또는 갱신한다."
	)
	@ApiImplicitParam(name = "Authorization", value = "Access token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer ${ACCESS_TOKEN}")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success", responseContainer = "List", response = OrderResponseDto.class),
		@ApiResponse(code = 401, message = "Member is not verified", response = DataNotVerifiedException.class),
		@ApiResponse(code = 404, message = "Api key is not found", response = DataNotFoundException.class),
	})
	@PostMapping("")
	public Mono<ResponseEntity<List<OrderResponseDto>>> upsertOrders(@RequestBody @Valid @ApiParam(value = "등록할 주문 정보", required = true) List<OrderDto> orderDtos, Principal principal) {
		return orderService.upsertOrders(principal.getName(), orderDtos);
	}

	@ApiOperation(
		value = "주문(매수, 매도, 역지정) 정보 제공",
		httpMethod = "GET",
		notes = "트레이더가 저장한 주문 정보를 제공한다. 트레이더의 거래소 api key를 기반으로 등록되어 있는 모든 주문 정보가 제공된다."
	)
	@ApiImplicitParam(name = "Authorization", value = "Access token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer ${ACCESS_TOKEN}")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success", responseContainer = "List", response = OrderResponseDto.class),
		@ApiResponse(code = 401, message = "Member is not verified", response = DataNotVerifiedException.class),
		@ApiResponse(code = 404, message = "Api key is not found", response = DataNotFoundException.class),
	})
	@GetMapping("")
	public Mono<ResponseEntity<List<OrderResponseDto>>> getOrders(Principal principal) {
		return orderService.getOrdersByApiKey(principal.getName());
	}
}
