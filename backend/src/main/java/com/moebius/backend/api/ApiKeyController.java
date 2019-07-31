package com.moebius.backend.api;

import com.moebius.backend.dto.frontend.ApiKeyDto;
import com.moebius.backend.service.member.ApiKeyService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiKeyController {
	private final ApiKeyService apiKeyService;

	@PostMapping("/api-key")
	public Mono<ResponseEntity<String>> createApiKey(@RequestBody @Valid ApiKeyDto apiKeyDto) {
		return apiKeyService.createApiKey(apiKeyDto);
	}

	@GetMapping("/api-keys/member/{memberId}")
	public Flux<ResponseEntity<ApiKeyDto>> getApiKeys(@PathVariable ObjectId memberId) {
		return apiKeyService.getApiKeysByMemberId(memberId);
	}

	@PutMapping("/api-keys/{id}")
	public Mono<ResponseEntity<ApiKeyDto>> updateApiKey(@PathVariable ObjectId apiKeyId, @RequestBody @Valid ApiKeyDto newApiKeyDto) {
		return apiKeyService.updateApiKey(apiKeyId, newApiKeyDto);
	}

	@DeleteMapping("/api-keys/{id}")
	public Mono<ResponseEntity<String>> deleteApiKey(@PathVariable ObjectId apiKeyId) {
		return apiKeyService.deleteApiKey(apiKeyId);
	}

	@PostMapping("/api-keys/{id}/verification")
	public Mono<ResponseEntity<?>> verifyApiKey(@PathVariable ObjectId apiKeyId) {
		return apiKeyService.verifyApiKey(apiKeyId);
	}

}
