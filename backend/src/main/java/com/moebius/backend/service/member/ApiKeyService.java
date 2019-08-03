package com.moebius.backend.service.member;

import com.moebius.backend.assembler.ApiKeyAssembler;
import com.moebius.backend.domain.apikeys.ApiKeyRepository;
import com.moebius.backend.dto.frontend.ApiKeyDto;
import com.moebius.backend.exception.DataNotFoundException;
import com.moebius.backend.exception.DuplicateDataException;
import com.moebius.backend.exception.ExceptionTypes;
import com.moebius.backend.service.exchange.ExchangeFactory;
import com.moebius.backend.service.exchange.ExchangeService;
import com.moebius.backend.utils.Verifier;
import com.mongodb.DuplicateKeyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.moebius.backend.utils.ThreadScheduler.COMPUTE;
import static com.moebius.backend.utils.ThreadScheduler.IO;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiKeyService {
	private final ApiKeyRepository apiKeyRepository;
	private final ApiKeyAssembler apiKeyAssembler;
	private final ExchangeFactory exchangeFactory;

	public Mono<ResponseEntity<String>> createApiKey(ApiKeyDto apiKeyDto) {
		Verifier.checkNullFields(apiKeyDto);

		return apiKeyRepository.save(apiKeyAssembler.toApiKey(apiKeyDto))
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.onErrorMap(exception -> exception instanceof DuplicateKeyException ?
				new DuplicateDataException(ExceptionTypes.DUPLICATE_DATA.getMessage(apiKeyDto.getName())) :
				exception)
			.map(apiKey -> ResponseEntity.ok(HttpStatus.OK.getReasonPhrase()));
	}

	public Flux<ResponseEntity<ApiKeyDto>> getApiKeysByMemberId(String memberId) {
		Verifier.checkNullFields(memberId);

		return apiKeyRepository.findAllByMemberId(new ObjectId(memberId))
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.switchIfEmpty(Flux.defer(() -> Flux.error(new DataNotFoundException(
				ExceptionTypes.NONEXISTENT_DATA.getMessage("[ApiKeys] Api key based on memberId(" + memberId + ")")))))
			.map(apiKey -> ResponseEntity.ok(apiKeyAssembler.toDto(apiKey)));
	}

	public Mono<ResponseEntity<String>> deleteApiKeyById(String id) {
		Verifier.checkNullFields(id);

		return apiKeyRepository.deleteById(new ObjectId(id))
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.map(aVoid -> ResponseEntity.ok(id));
	}

	public Mono<ResponseEntity<String>> verifyApiKey(String id) {
		Verifier.checkNullFields(id);

		return apiKeyRepository.findById(new ObjectId(id))
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.log()
			.switchIfEmpty(Mono.defer(() -> Mono.error(new DataNotFoundException(
				ExceptionTypes.NONEXISTENT_DATA.getMessage("[ApiKeys] Api key")))))
			.flatMap(apiKey -> {
				ExchangeService exchangeService = exchangeFactory.getService(apiKey.getExchange());
				return exchangeService.getAuthToken(apiKey)
					.flatMap(exchangeService::doHealthCheck);
			});
	}
}
