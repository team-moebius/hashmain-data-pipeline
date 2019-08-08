package com.moebius.backend.service.exchange;

import com.moebius.backend.domain.commons.Exchange;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

public interface ExchangeService {
	Exchange getExchange();

	Mono<String> getAuthToken(String accessKey, String secretKey);

	Mono<ClientResponse> doHealthCheck(String authToken);
}
