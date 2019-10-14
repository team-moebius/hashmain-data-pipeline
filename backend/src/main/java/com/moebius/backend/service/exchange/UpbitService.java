package com.moebius.backend.service.exchange;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.dto.exchange.UpbitOrderDto;
import com.moebius.backend.exception.ExceptionTypes;
import com.moebius.backend.exception.VerificationFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.moebius.backend.utils.ThreadScheduler.COMPUTE;
import static com.moebius.backend.utils.ThreadScheduler.IO;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpbitService implements ExchangeService<UpbitOrderDto> {
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
	public Mono<String> getAuthToken(String accessKey, String secretKey) {
		log.info("[ApiKey] Start to get upbit auth token.");

		return Mono.fromCallable(() -> {
			Algorithm algorithm = Algorithm.HMAC256(secretKey);
			return JWT.create()
				.withClaim("access_key", accessKey)
				.withClaim("nonce", LocalDateTime.now().toString())
				.sign(algorithm);
		}).subscribeOn(COMPUTE.scheduler());
	}

	@Override
	public Mono<ClientResponse> doHealthCheck(String authToken) {
		log.info("[ApiKey] Start to do health check.");

		return webClient.get()
			.uri(publicUri + assetUri)
			.headers(httpHeaders -> httpHeaders.setBearerAuth(authToken))
			.exchange()
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.filter(clientResponse -> clientResponse.statusCode() == HttpStatus.OK)
			.switchIfEmpty(Mono.defer(() -> Mono.error(new VerificationFailedException(ExceptionTypes.UNVERIFIED_DATA.getMessage("AuthToken")))));
	}

	@Override
	public Mono<ClientResponse> doOrder(UpbitOrderDto exchangeOrderDto) {
		return null;
	}

}
