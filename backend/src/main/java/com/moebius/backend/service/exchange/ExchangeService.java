package com.moebius.backend.service.exchange;

import com.moebius.backend.domain.apikeys.ApiKey;
import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.orders.Order;
import com.moebius.backend.dto.OrderStatusDto;
import com.moebius.backend.dto.exchange.AssetDto;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExchangeService {
	Exchange getExchange();

	Mono<String> getAuthToken(String accessKey, String secretKey);

	Flux<? extends AssetDto> getAssets(String authToken);

	Mono<ClientResponse> checkHealth(String authToken);

	Mono<ClientResponse> order(ApiKey apiKey, Order order);

	Mono<OrderStatusDto> getCurrentOrderStatus(ApiKey apiKey, Order order);
}
