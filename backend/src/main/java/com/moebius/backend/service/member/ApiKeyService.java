package com.moebius.backend.service.member;

import com.moebius.backend.assembler.ApiKeyAssembler;
import com.moebius.backend.domain.apikeys.ApiKeyRepository;
import com.moebius.backend.dto.frontend.ApiKeyDto;
import com.moebius.backend.dto.frontend.response.ApiKeyResponseDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.moebius.backend.utils.ThreadScheduler.COMPUTE;
import static com.moebius.backend.utils.ThreadScheduler.IO;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiKeyService {
	private final ApiKeyRepository apiKeyRepository;
	private final ApiKeyAssembler apiKeyAssembler;
	private final ExchangeFactory exchangeFactory;

	public Mono<ResponseEntity<ApiKeyResponseDto>> createApiKey(ApiKeyDto apiKeyDto, String memberId) {
		Verifier.checkNullFields(apiKeyDto);
		Verifier.checkBlankString(memberId);

		return apiKeyRepository.save(apiKeyAssembler.toApiKey(apiKeyDto, memberId))
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.onErrorMap(exception -> exception instanceof DuplicateKeyException ?
				new DuplicateDataException(ExceptionTypes.DUPLICATE_DATA.getMessage(apiKeyDto.getName())) :
				exception)
			.map(apiKeyAssembler::toResponseDto)
			.map(ResponseEntity::ok);
	}

	// TODO : Refactor return type as simplified one. (ServerResponse)
	public Mono<ResponseEntity<List<ApiKeyResponseDto>>> getApiKeysByMemberId(String memberId) {
		Verifier.checkBlankString(memberId);

		return apiKeyRepository.findAllByMemberId(new ObjectId(memberId))
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.switchIfEmpty(Mono.defer(() -> Mono.error(new DataNotFoundException(
				ExceptionTypes.NONEXISTENT_DATA.getMessage("[ApiKeys] Api key based on memberId(" + memberId + ")")))))
			.map(apiKeyAssembler::toResponseDto)
			.collectList()
			.map(ResponseEntity::ok);
	}

	public Mono<ResponseEntity<String>> deleteApiKeyById(String id, String memberId) {
		Verifier.checkNullFields(id);

		return apiKeyRepository.deleteByIdAndMemberId(new ObjectId(id), new ObjectId(memberId))
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.onErrorMap(exception -> {
				log.error("[ApiKeys] Deletion failed", exception);
				return new DataNotFoundException(ExceptionTypes.NONEXISTENT_DATA.getMessage("[ApiKeys] Api key"));
			})
			.map(aLong -> ResponseEntity.ok(id));
	}

	public Mono<ResponseEntity<String>> verifyApiKey(String id, String memberId) {
		Verifier.checkNullFields(id);

		return apiKeyRepository.findByIdAndMemberId(new ObjectId(id), new ObjectId(memberId))
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.switchIfEmpty(Mono.defer(() -> Mono.error(new DataNotFoundException(
				ExceptionTypes.NONEXISTENT_DATA.getMessage("[ApiKeys] Api key")))))
			.flatMap(apiKey -> {
				ExchangeService exchangeService = exchangeFactory.getService(apiKey.getExchange());
				return exchangeService.getAuthToken(apiKey)
					.flatMap(exchangeService::doHealthCheck);
			});
	}
}
