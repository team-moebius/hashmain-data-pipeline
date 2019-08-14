package com.moebius.backend.service.stoploss;

import com.moebius.backend.assembler.MarketAssembler;
import com.moebius.backend.assembler.StoplossAssembler;
import com.moebius.backend.domain.apikeys.ApiKeyRepository;
import com.moebius.backend.domain.markets.MarketRepository;
import com.moebius.backend.domain.stoplosses.Stoploss;
import com.moebius.backend.domain.stoplosses.StoplossRepository;
import com.moebius.backend.dto.frontend.StoplossDto;
import com.moebius.backend.exception.DataNotFoundException;
import com.moebius.backend.exception.ExceptionTypes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.moebius.backend.utils.ThreadScheduler.COMPUTE;
import static com.moebius.backend.utils.ThreadScheduler.IO;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoplossService {
	private final StoplossRepository stoplossRepository;
	private final ApiKeyRepository apiKeyRepository;
	private final MarketRepository marketRepository;
	private final StoplossAssembler stoplossAssembler;
	private final MarketAssembler marketAssembler;

	public Flux<ResponseEntity<?>> createStoplosses(ObjectId apiKeyId, List<StoplossDto> stoplossDtos) {
		return apiKeyRepository.findById(apiKeyId)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.switchIfEmpty(Mono.defer(
				() -> Mono.error(new DataNotFoundException(ExceptionTypes.NONEXISTENT_DATA.getMessage("[ApiKey] " + apiKeyId.toString())))))
			.flatMapIterable(apiKey -> stoplossAssembler.toStoplosses(apiKey, stoplossDtos))
			.compose(this::saveStoplosses)
			.flatMap(this::saveMarket);
	}

	public Flux<ResponseEntity<StoplossDto>> getStoplossesByApiKey(ObjectId apiKeyId) {
		return stoplossRepository.findAllByApiKeyId(apiKeyId)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.switchIfEmpty(Flux.defer(() -> Flux.error(new DataNotFoundException(
				ExceptionTypes.NONEXISTENT_DATA.getMessage("[Stoploss] Stoploss information based on  " + apiKeyId.toString())))))
			.map(stoploss -> ResponseEntity.ok(stoplossAssembler.toDto(stoploss)));
	}

	public Mono<ResponseEntity<String>> deleteStoplossById(ObjectId id) {
		return stoplossRepository.findById(id)
			.flatMap(stoploss -> marketRepository.deleteByExchangeAndSymbol(stoploss.getExchange(), stoploss.getSymbol()))
			.map(aVoid -> stoplossRepository.deleteById(id))
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.map(aVoid -> ResponseEntity.ok(id.toHexString()));
	}

	private Flux<Stoploss> saveStoplosses(Flux<Stoploss> stoplosses) {
		return stoplossRepository.saveAll(stoplosses)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler());
	}

	private Mono<ResponseEntity<?>> saveMarket(Stoploss stoploss) {
		return marketRepository.save(marketAssembler.toMarketFromStoploss(stoploss))
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.map(market -> ResponseEntity.ok(stoploss.getId()));
	}

}
