package com.moebius.backend.service.exchange;

import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.orders.Order;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

public interface ExchangeService<T> {
	Exchange getExchange();

	Mono<String> getAuthToken(String accessKey, String secretKey);

	Mono<ClientResponse> checkHealth(String authToken);

	Mono<ClientResponse> order(Order order);
}
