package com.moebius.backend.service.exchange;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.moebius.backend.assembler.exchange.UpbitAssembler;
import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.orders.Order;
import com.moebius.backend.dto.exchange.AssetDto;
import com.moebius.backend.dto.exchange.upbit.UpbitAssetDto;
import com.moebius.backend.dto.exchange.upbit.UpbitOrderDto;
import com.moebius.backend.exception.ExceptionTypes;
import com.moebius.backend.exception.WrongDataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.moebius.backend.utils.ThreadScheduler.COMPUTE;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpbitService implements ExchangeService {
	@Value("${exchange.upbit.rest.public-uri}")
	private String publicUri;
	@Value("${exchange.upbit.rest.asset}")
	private String assetUri;
	@Value("${exchange.upbit‘.rest.orders}")
	private String ordersUri;
	@Value("${exchange.upbit‘.rest.order}")
	private String orderUri;

	private final WebClient webClient;
	private final UpbitAssembler upbitAssembler;

	@Override
	public Exchange getExchange() {
		return Exchange.UPBIT;
	}

	@Override
	public Mono<String> getAuthToken(String accessKey, String secretKey) {
		log.info("[Upbit] Start to get auth token. [accessKey: {}, secretKey: {}]", accessKey, secretKey);

		return Mono.fromCallable(() -> {
			Algorithm algorithm = Algorithm.HMAC256(secretKey);
			return JWT.create()
				.withClaim("access_key", accessKey)
				.withClaim("nonce", LocalDateTime.now().toString())
				.sign(algorithm);
		}).subscribeOn(COMPUTE.scheduler());
	}

	@Override
	public Flux<? extends AssetDto> getAssets(String authToken) {
		return webClient.get()
			.uri(publicUri + assetUri)
			.headers(httpHeaders -> httpHeaders.setBearerAuth(authToken))
			.retrieve()
			.bodyToFlux(UpbitAssetDto.class);
	}

	@Override
	public Mono<ClientResponse> checkHealth(String authToken) {
		log.info("[Upbit] Start to do health check. [authToken: {}]", authToken);

		return webClient.get()
			.uri(publicUri + assetUri)
			.headers(httpHeaders -> httpHeaders.setBearerAuth(authToken))
			.exchange()
			.filter(clientResponse -> clientResponse.statusCode() == HttpStatus.OK)
			.switchIfEmpty(Mono.defer(() -> Mono.error(new WrongDataException(ExceptionTypes.UNVERIFIED_DATA.getMessage("Auth token")))));
	}

	@Override
	public Mono<ClientResponse> order(String authToken, Order order) {
		log.info("[Upbit] Start to request order. [{}]", order);

		return webClient.post()
			.uri(publicUri + ordersUri)
			.headers(httpHeaders -> httpHeaders.setBearerAuth(authToken))
			.body(getOrderBody(order), UpbitOrderDto.class)
			.exchange()
			.publishOn(COMPUTE.scheduler());
	}

	@Override
	public Mono<OrderStatusDto> getOrderStatus(String authToken, Order order) {
		log.info("[Upbit] Start to update order status. [{}])", order);

		return webClient.get()
			.uri(publicUri + orderUri)
			.headers(httpHeaders -> httpHeaders.setBearerAuth(authToken))
			.exchange()
			.filter(clientResponse -> clientResponse.statusCode() == HttpStatus.OK)
			.switchIfEmpty(Mono.defer(() -> Mono.error(new WrongDataException(ExceptionTypes.UNVERIFIED_DATA.getMessage("Auth token")))));
	}

	private Mono<UpbitOrderDto> getOrderBody(Order order) {
		return Mono.fromCallable(() -> upbitAssembler.toOrderDto(order))
			.subscribeOn(COMPUTE.scheduler());
	}
}
