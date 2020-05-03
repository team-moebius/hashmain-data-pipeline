package com.moebius.backend.service.exchange;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.moebius.backend.assembler.exchange.UpbitAssembler;
import com.moebius.backend.domain.apikeys.ApiKey;
import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.orders.Order;
import com.moebius.backend.dto.OrderStatusDto;
import com.moebius.backend.dto.exchange.AssetDto;
import com.moebius.backend.dto.exchange.upbit.UpbitAssetDto;
import com.moebius.backend.dto.exchange.upbit.UpbitOrderDto;
import com.moebius.backend.dto.exchange.upbit.UpbitOrderStatusDto;
import com.moebius.backend.exception.ExceptionTypes;
import com.moebius.backend.exception.WrongDataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import static com.moebius.backend.utils.ThreadScheduler.COMPUTE;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpbitService implements ExchangeService {
	@Value("${exchange.upbit.rest.public-uri}")
	private String publicUri;
	@Value("${exchange.upbit.rest.asset}")
	private String assetUri;
	@Value("${exchange.upbit.rest.orders}")
	private String ordersUri;
	@Value("${exchange.upbit.rest.order}")
	private String orderUri;
	@Value("${exchange.upbit.rest.identifier}")
	private String identifierUri;
	@Value("${exchange.upbit.message-digest.hash-algorithm")
	private String messageDigestHashAlgorithm;
	@Value("${exchange.upbit.message-digest.charset")
	private String messageDigestCharset;

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
				.withClaim("nonce", UUID.randomUUID().toString())
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
	public Mono<ClientResponse> order(ApiKey apiKey, Order order) {
		log.info("[Upbit] Start to request order. [{}]", order);
		String queryParameter = upbitAssembler.assembleOrderParameters(order);
		String token = getAuthTokenWithParameter(apiKey, queryParameter);

		return webClient.post()
			.uri(publicUri + ordersUri)
			.headers(httpHeaders -> httpHeaders.setBearerAuth(token))
			.body(getOrderBody(order), UpbitOrderDto.class)
			.exchange()
			.publishOn(COMPUTE.scheduler());
	}

	@Override
	public Mono<OrderStatusDto> getCurrentOrderStatus(ApiKey apiKey, Order order) {
		log.info("[Upbit] Start to get updated order status. [{}])", order);
		String queryParameter = upbitAssembler.assembleOrderIdentifier(order.getId().toHexString());
		String token = getAuthTokenWithParameter(apiKey, queryParameter);

		return webClient.get()
			.uri(publicUri + orderUri + identifierUri + order.getId().toHexString())
			.headers(httpHeaders -> httpHeaders.setBearerAuth(token))
			.retrieve()
			.onStatus(HttpStatus.UNAUTHORIZED::equals,
				response -> Mono.error(new WrongDataException(ExceptionTypes.UNVERIFIED_DATA.getMessage("Auth token (" + apiKey + ")"))))
			.bodyToMono(UpbitOrderStatusDto.class)
			.map(upbitOrderStatusDto -> upbitAssembler.toOrderStatusDto(order, upbitOrderStatusDto));
	}

	private String getAuthTokenWithParameter(ApiKey apiKey, String query) {
		log.info("[Upbit] Start to get auth token. [memberId: {}, query: {}]", apiKey.getMemberId(), query);

		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance(messageDigestHashAlgorithm);
			messageDigest.update(query.getBytes(messageDigestCharset));
		} catch (NoSuchAlgorithmException e) {
			log.error("[Upbit] Cannot find message digest hash algorithm.", e);
			return StringUtils.EMPTY;
		} catch (UnsupportedEncodingException e) {
			log.error("[Upbit] Cannot support encoding.", e);
			return StringUtils.EMPTY;
		}

		String queryHash = String.format("%0128x", new BigInteger(1, messageDigest.digest()));

		Algorithm algorithm = Algorithm.HMAC256(apiKey.getSecretKey());

		return JWT.create()
			.withClaim("access_key", apiKey.getAccessKey())
			.withClaim("nonce", UUID.randomUUID().toString())
			.withClaim("query_hash", queryHash)
			.withClaim("query_hash_alg", messageDigestHashAlgorithm)
			.sign(algorithm);
	}

	private Mono<UpbitOrderDto> getOrderBody(Order order) {
		return Mono.fromCallable(() -> upbitAssembler.toOrderDto(order))
			.subscribeOn(COMPUTE.scheduler());
	}
}
