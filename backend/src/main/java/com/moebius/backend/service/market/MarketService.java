package com.moebius.backend.service.market;

import com.moebius.backend.assembler.MarketAssembler;
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
}
