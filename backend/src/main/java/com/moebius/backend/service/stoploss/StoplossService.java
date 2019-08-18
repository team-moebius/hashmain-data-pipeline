package com.moebius.backend.service.stoploss;

import com.moebius.backend.assembler.StoplossAssembler;
import com.moebius.backend.domain.apikeys.ApiKeyRepository;
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
	private final StoplossAssembler stoplossAssembler;

	public Flux<ResponseEntity<?>> createStoplosses(ObjectId apiKeyId, List<StoplossDto> stoplossDtos) {
		return apiKeyRepository.findById(apiKeyId)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.switchIfEmpty(Mono.defer(() -> Mono.error(new DataNotFoundException(ExceptionTypes.NONEXISTENT_DATA.getMessage("[ApiKey] " + apiKeyId.toString())))))
			.flatMapIterable(apiKey -> stoplossAssembler.toStoplosses(apiKey, stoplossDtos))
			.compose(this::saveStoplosses);
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
		return stoplossRepository.deleteById(id)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.map(aVoid -> ResponseEntity.ok(id.toHexString()));
	}

	private Flux<ResponseEntity<?>> saveStoplosses(Flux<Stoploss> stoplossFlux) {
		return stoplossRepository.saveAll(stoplossFlux)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.map(stoploss -> ResponseEntity.ok(stoploss.getId()));
	}
}
