package com.moebius.backend.service.member;

import com.moebius.backend.dto.frontend.ApiKeyDto;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ApiKeyService {
	public Mono<ResponseEntity<String>> createApiKey(ApiKeyDto apiKeyDto) {
		return null;
	}

	public Flux<ResponseEntity<ApiKeyDto>> getApiKeysByMemberId(ObjectId memberId) {
		return null;
	}

	public Mono<ResponseEntity<ApiKeyDto>> updateApiKey(ObjectId apiKeyId, ApiKeyDto newApiKeyDto) {
		return null;
	}

	public Mono<ResponseEntity<String>> deleteApiKey(ObjectId apiKeyId) {
		return null;
	}

	public Mono<ResponseEntity<?>> verifyApiKey(ObjectId apiKeyId) {
		return null;
	}
}
