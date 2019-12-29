package com.moebius.backend.service.market;

import com.moebius.backend.assembler.MarketAssembler;
import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.markets.Market;
import com.moebius.backend.domain.markets.MarketRepository;
import com.moebius.backend.dto.MarketDto;
import com.moebius.backend.dto.exchange.MarketsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.moebius.backend.utils.ThreadScheduler.COMPUTE;
import static com.moebius.backend.utils.ThreadScheduler.IO;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketService {
	@Value("${exchange.upbit.rest.public-uri}")
	private String publicUri;
	@Value("${exchange.upbit.rest.market}")
	private String marketUri;

	private final WebClient webClient;
	private final MarketRepository marketRepository;
	private final MarketAssembler marketAssembler;

	public Mono<ResponseEntity<String>> createMarket(MarketDto marketDto) {
		return marketRepository.save(marketAssembler.toMarket(marketDto))
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.map(market -> ResponseEntity.ok(market.getId().toString()));
	}

	public Mono<ResponseEntity<String>> deleteMarket(String id) {
		return marketRepository.deleteById(new ObjectId(id))
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.map(aVoid -> {
				log.info("[Market] The market has been deleted. [id : {}]", id);
				return ResponseEntity.ok().build();
			});
	}

	public Mono<ResponseEntity<?>> updateMarketsByExchange(Exchange exchange) {
		return webClient.get()
			.uri(publicUri + marketUri)
			.retrieve()
			.bodyToMono(MarketsDto.class)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.map(marketsDto -> marketAssembler.toMarkets(exchange, marketsDto))
			.map(markets -> {
				markets.stream()
					.filter(market -> market.getSymbol().startsWith("KRW"))
					.forEach(market -> createMarketIfNotExist(market.getExchange(), market.getSymbol()).subscribe());
				return ResponseEntity.ok().build();
			});
	}

	private Mono<Boolean> createMarketIfNotExist(Exchange exchange, String symbol) {
		return marketRepository.findByExchangeAndSymbol(exchange, symbol)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.hasElement()
			.flatMap(exist -> exist ? Mono.just(Boolean.FALSE) : saveMarket(marketAssembler.toMarket(exchange, symbol)));
	}

	private Mono<Boolean> saveMarket(Market market) {
		return marketRepository.save(market)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.map(createdMarket -> {
				log.info("[Market] {} / {} is not found, The new market will be saved.", market.getExchange(), market.getSymbol());
				return true;
			});
	}
}
