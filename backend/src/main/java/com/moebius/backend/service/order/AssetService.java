package com.moebius.backend.service.order;

import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.dto.AssetDto;
import com.moebius.backend.dto.AssetsDto;
import com.moebius.backend.service.member.ApiKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.moebius.backend.utils.ThreadScheduler.COMPUTE;
import static com.moebius.backend.utils.ThreadScheduler.IO;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssetService {
	@Value("${exchange.upbit.rest.public-uri}")
	private String publicUri;
	@Value("${exchange.upbit.rest.asset}")
	private String assetUri;

	private final ApiKeyService apiKeyService;
	private final WebClient webClient;

	public Mono<List<AssetDto>> getAssetsByMemberId(String memberId, Exchange exchange) {
		return apiKeyService.getExchangeAuthToken(memberId, Exchange.UPBIT)
			.subscribeOn(COMPUTE.scheduler())
			.flatMap(this::getAssets);
	}

	private Mono<AssetsDto> getAssets(String authToken) {
		return webClient.get()
			.uri(publicUri + assetUri)
			.headers(httpHeaders -> httpHeaders.setBearerAuth(authToken))
			.retrieve()
			.bodyToMono(AssetsDto.class)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler());
	}
}
