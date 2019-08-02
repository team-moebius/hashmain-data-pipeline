package com.moebius.backend.api;

import com.moebius.backend.dto.frontend.ApiKeyDto;
import com.moebius.backend.exception.DataNotFoundException;
import com.moebius.backend.exception.DuplicateDataException;
import com.moebius.backend.service.member.ApiKeyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/api-keys")
@RequiredArgsConstructor
public class ApiKeyController {
	private final ApiKeyService apiKeyService;

	@ApiOperation(
		value = "Api key 생성",
		httpMethod = "POST",
		notes = "거래에 사용될 Api key를 등록한다."
	)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success", response = String.class),
		@ApiResponse(code = 400, message = "Requested api key already exists", response = DuplicateDataException.class),
	})
	@PostMapping("")
	public Mono<ResponseEntity<String>> createApiKey(@RequestBody @Valid ApiKeyDto apiKeyDto) {
		return apiKeyService.createApiKey(apiKeyDto);
	}

	@ApiOperation(
		value = "Api keys 조회",
		httpMethod = "GET",
		notes = "거래에 사용될 Api Keys를 조회한다."
	)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success", responseContainer = "List", response = ApiKeyDto.class),
		@ApiResponse(code = 404, message = "Api keys are not found at all", response = DataNotFoundException.class),
	})
	@GetMapping("/member/{memberId}")
	public Flux<ResponseEntity<ApiKeyDto>> getApiKeys(@PathVariable ObjectId memberId) {
		return apiKeyService.getApiKeysByMemberId(memberId);
	}

	@ApiOperation(
		value = "Api key 삭제",
		httpMethod = "DELETE",
		notes = "거래에 사용되는 특정 Api Key를 삭제한다."
	)
	@ApiResponses({
		@ApiResponse(code = 200, message = "The deleted api key id", response = String.class),
	})
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<String>> deleteApiKey(@PathVariable ObjectId id) {
		return apiKeyService.deleteApiKeyById(id);
	}

	@ApiOperation(
		value = "Api key 검증",
		httpMethod = "POST",
		notes = "거래에 사용되는 Api key를 검증한다."
	)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success", response = String.class),
		@ApiResponse(code = 400, message = "Requested api key is not found", response = DataNotFoundException.class),
	})
	@PostMapping("/verification")
	public Mono<ResponseEntity<String>> verifyApiKey(@RequestBody @Valid ObjectId id) {
		return apiKeyService.verifyApiKey(id);
	}

}
