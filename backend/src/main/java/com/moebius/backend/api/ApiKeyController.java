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
@RequestMapping("/api/api-keys")
@RequiredArgsConstructor
public class ApiKeyController {
	private final ApiKeyService apiKeyService;

	@PostMapping("/")
	public Mono<ResponseEntity<String>> createApiKey(@RequestBody @Valid ApiKeyDto apiKeyDto) {
		return apiKeyService.createApiKey(apiKeyDto);
	}

	@GetMapping("/member/{memberId}")
	public Flux<ResponseEntity<ApiKeyDto>> getApiKeys(@PathVariable ObjectId memberId) {
		return apiKeyService.getApiKeysByMemberId(memberId);
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<String>> deleteApiKey(@PathVariable ObjectId id) {
		return apiKeyService.deleteApiKeyById(id);
	}

	@PostMapping("/verification")
	public Mono<ResponseEntity<String>> verifyApiKey(@RequestBody @Valid ObjectId id) {
		return apiKeyService.verifyApiKey(id);
	}

}
