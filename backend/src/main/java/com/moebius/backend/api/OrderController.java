package com.moebius.backend.api;

import com.moebius.backend.dto.OrderDto;
import com.moebius.backend.dto.frontend.response.OrderResponseDto;
import com.moebius.backend.exception.DataNotFoundException;
import com.moebius.backend.service.order.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
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
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success", responseContainer = "List", response = OrderResponseDto.class),
		@ApiResponse(code = 404, message = "Api key is not found", response = DataNotFoundException.class),
	})
	@PostMapping("/api-keys/{apiKeyId}")
	public Mono<ResponseEntity<List<OrderResponseDto>>> upsertOrders(
		@PathVariable @ApiParam(value = "트레이더의 거래소 api key id, 트레이더의 거래소 api key는 연관된 member api로 조회해서 가지고 온다.", required = true) String apiKeyId,
		@RequestBody @Valid @ApiParam(value = "등록할 스탑로스 정보", required = true) List<OrderDto> orderDtos) {
		return orderService.upsertOrders(apiKeyId, orderDtos);
	}

	@ApiOperation(
		value = "주문(매수, 매도, 역지정) 정보 제공",
		httpMethod = "GET",
		notes = "트레이더가 저장한 주문 정보를 제공한다. 트레이더의 거래소 api key를 기반으로 등록되어 있는 모든 주문 정보가 제공된다."
	)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success", responseContainer = "List", response = OrderResponseDto.class),
		@ApiResponse(code = 404, message = "Api key is not found", response = DataNotFoundException.class),
	})
	@GetMapping("/api-keys/{apiKey}")
	public Mono<ResponseEntity<List<OrderResponseDto>>> getOrders(
		@PathVariable @ApiParam(value = "트레이더 거래소 api key", required = true) String apiKeyId) {
		return orderService.getOrdersByApiKey(apiKeyId);
	}
}
