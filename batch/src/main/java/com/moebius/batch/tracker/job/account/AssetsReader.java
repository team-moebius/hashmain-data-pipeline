package com.moebius.batch.tracker.job.account;

import com.moebius.batch.tracker.configuration.UpbitProperties;
import com.moebius.batch.tracker.dto.Assets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@StepScope
public class AssetsReader extends AbstractItemCountingItemStreamItemReader<Assets> { // TODO : Maybe change base class to JsonItemReader
	private static final int MAX_RETRY_COUNT = 1;

	private final WebClient webClient;
	private final UpbitProperties upbitProperties;
	private final String upbitJwtAuthToken;

	public AssetsReader(WebClient webClient, UpbitProperties upbitProperties, String upbitJwtAuthToken) {
		setName("AssetsReader");
		setMaxItemCount(MAX_RETRY_COUNT);
		this.webClient = webClient;
		this.upbitProperties = upbitProperties;
		this.upbitJwtAuthToken = upbitJwtAuthToken;
	}

	@Override
	protected Assets doRead() {
		log.info("Start to read assets from exchanges ...");
		return webClient.get()
			.uri(upbitProperties.getUriInfo().getOpenedHost() + upbitProperties.getUriInfo().getAsset())
			.headers(HttpHeaders -> HttpHeaders.setBearerAuth(upbitJwtAuthToken))
			.retrieve()
			.bodyToMono(Assets.class)
			.block();
	}

	@Override
	protected void doOpen() {
	}

	@Override
	protected void doClose() {
	}
}
