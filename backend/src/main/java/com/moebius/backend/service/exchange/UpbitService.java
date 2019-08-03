package com.moebius.backend.service.exchange;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.moebius.backend.domain.apikeys.ApiKey;
import com.moebius.backend.domain.commons.Exchange;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.moebius.backend.utils.ThreadScheduler.COMPUTE;

@Service
@RequiredArgsConstructor
public class UpbitService implements ExchangeService {
	private static final String AUTH_PREFIX = "Bearer ";
	@Value("${exchange.upbit.rest.public-uri}")
	private String publicUri;
	@Value("${exchange.upbit.rest.asset}")
	private String assetUri;

	private final WebClient webClient;

	@Override
	public Exchange getExchange() {
		return Exchange.UPBIT;
	}

	@Override
	public Mono<String> getAuthToken(ApiKey apiKey) {
		return Mono.fromCallable(() -> {
			Algorithm algorithm = Algorithm.HMAC256(apiKey.getSecretKey());
			return JWT.create()
				.withClaim("access_key", apiKey.getAccessKey())
				.withClaim("nonce", LocalDateTime.now().toString())
				.sign(algorithm);
		}).subscribeOn(COMPUTE.scheduler());
	}

	@Override
	public Mono<ResponseEntity<String>> doHealthCheck(String authToken) {
		return webClient.get()
			.uri(publicUri + assetUri)
			.headers(httpHeaders -> httpHeaders.setBearerAuth(authToken))
			.exchange()
			.map(clientResponse -> ResponseEntity.ok(HttpStatus.OK.getReasonPhrase())); // FIXME : Add exception handler ...
	}
}
