package com.moebius.backend.service.market;

import com.moebius.backend.assembler.MarketAssembler;
import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.commons.Symbol;
import com.moebius.backend.domain.markets.Market;
import com.moebius.backend.domain.markets.MarketRepository;
import com.moebius.backend.dto.MarketDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.moebius.backend.utils.ThreadScheduler.COMPUTE;
import static com.moebius.backend.utils.ThreadScheduler.IO;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketService {
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
			.map(aVoid -> ResponseEntity.ok().build());
	}

	public Mono<Market> getMarket(Exchange exchange, Symbol symbol) {
		return marketRepository.findByExchangeAndSymbol(exchange, symbol)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler());
	}

	public Mono<Market> updateMarketActivation(Exchange exchange, Symbol symbol, boolean active) {
		return marketRepository.findByExchangeAndSymbol(exchange, symbol)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.switchIfEmpty(Mono.defer(() -> {
				log.info("[Market] {} / {} is not found.", exchange, symbol);
				return Mono.empty();
			}))
			.map(market -> {
				log.info("[Market] Update the market activation. [{} / {} : {}]", exchange, symbol, active);
				market.setActive(active);
				return market;
			})
			.flatMap(this::saveMarket);
	}

	private Mono<Market> saveMarket(Market market) {
		return marketRepository.save(market)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler());
	}
}
