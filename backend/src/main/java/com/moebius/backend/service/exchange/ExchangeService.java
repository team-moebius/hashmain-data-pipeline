package com.moebius.backend.service.exchange;

import com.moebius.backend.domain.apikeys.ApiKey;
import com.moebius.backend.domain.commons.Exchange;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface ExchangeService {
	Exchange getExchange();

	Mono<String> getAuthToken(ApiKey apiKey);

	Mono<ResponseEntity<String>> doHealthCheck(String authToken);
}
