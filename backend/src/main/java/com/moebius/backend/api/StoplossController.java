package com.moebius.backend.api;

import com.moebius.backend.dto.frontend.StoplossDto;
import com.moebius.backend.dto.frontend.response.StoplossResponseDto;
import com.moebius.backend.service.order.StoplossService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/stoplosses")
@RequiredArgsConstructor
public class StoplossController {
	private final StoplossService stoplossService;

	@ApiOperation(
		value = "스탑로스 생성 / 갱신",
		httpMethod = "POST",
		notes = "트레이더가 원하는 스탑로스 정보를 일괄 생성 또는 갱신한다."
	)
	@PostMapping("/api-keys/{apiKeyId}")
	public Mono<ResponseEntity<List<StoplossResponseDto>>> upsertStoplosses(
		@PathVariable @ApiParam(value = "트레이더의 거래소 api key id, 트레이더의 거래소 api key는 연관된 member api로 조회해서 가지고 온다.", required = true) String apiKeyId,
		@RequestBody @Valid @ApiParam(value = "등록할 스탑로스 정보", required = true) List<StoplossDto> stoplossDtos) {
		return stoplossService.createStoplosses(apiKeyId, stoplossDtos);
	}

	@ApiOperation(
		value = "스탑로스 정보 제공",
		httpMethod = "GET",
		notes = "트레이더가 저장한 스탑로스 정보를 제공한다. 트레이더의 거래소 api key를 기반으로 등록되어 있는 모든 스탑로스 정보가 제공된다."
	)
	@GetMapping("/api-keys/{apiKey}")
	public Mono<ResponseEntity<List<StoplossResponseDto>>> getStoplosses(
		@PathVariable @ApiParam(value = "트레이더 거래소 api key", required = true) String apiKeyId) {
		return stoplossService.getStoplossesByApiKey(apiKeyId);
	}

	@ApiOperation(
		value = "스탑로스 삭제",
		httpMethod = "DELETE",
		notes = "트레이더의 특정 스탑로스 정보 한건을 삭제한다."
	)
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<String>> deleteStoploss(
		@PathVariable @ApiParam(value = "스탑로스 id", required = true) String id) {
		return stoplossService.deleteStoplossById(id);
	}
}
