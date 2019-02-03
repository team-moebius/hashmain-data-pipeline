package com.moebius.batch.tracker.job.step;

import com.moebius.batch.tracker.configuration.UpbitProperties;
import com.moebius.batch.tracker.dto.Asset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TrackerReader extends AbstractItemCountingItemStreamItemReader<Asset> {
	private final WebClient webClient;
	private final UpbitProperties upbitProperties;
	private final String upbitJwtAuthToken;

	public TrackerReader(WebClient webClient, UpbitProperties upbitProperties, String upbitJwtAuthToken) {
		setName("TrackerReader");
		this.webClient = webClient;
		this.upbitProperties = upbitProperties;
		this.upbitJwtAuthToken = upbitJwtAuthToken;
	}

	@Override
	protected Asset doRead() {
		log.info("TrackerReader start to read data from exchanges ...");

		Mono<Asset> assetMono = webClient.get()
			.uri(upbitProperties.getUriInfo().getOpenedHost() + upbitProperties.getUriInfo().getAsset())
			.headers(HttpHeaders -> HttpHeaders.setBearerAuth(upbitJwtAuthToken))
			.exchange()
			.flatMap(clientResponse -> {
				log.info("Received clientResponse successfully.");
				return clientResponse.bodyToMono(Asset.class);
			});

		return assetMono.block();
	}

	@Override
	protected void doOpen() {
	}

	@Override
	protected void doClose() {
	}
}
