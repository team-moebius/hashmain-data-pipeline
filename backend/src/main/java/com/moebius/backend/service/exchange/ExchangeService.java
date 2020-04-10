package com.moebius.backend.service.exchange;

import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.orders.Order;
import com.moebius.backend.dto.exchange.AssetDto;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ExchangeService {
	Exchange getExchange();

	Mono<String> getAuthToken(String accessKey, String secretKey);

	Mono<List<AssetDto>> getAssets(String authToken);

	Mono<ClientResponse> checkHealth(String authToken);

	Mono<ClientResponse> order(String authToken, Order order);
}
