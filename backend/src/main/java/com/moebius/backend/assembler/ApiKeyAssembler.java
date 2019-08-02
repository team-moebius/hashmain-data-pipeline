package com.moebius.backend.assembler;

import com.moebius.backend.domain.apikeys.ApiKey;
import com.moebius.backend.dto.frontend.ApiKeyDto;
import com.moebius.backend.utils.Verifier;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class ApiKeyAssembler {
	public ApiKey toApiKey(@NotNull ApiKeyDto apiKeyDto) {
		Verifier.checkNullField(apiKeyDto);

		ApiKey apiKey = new ApiKey();

		apiKey.setMemberId(apiKeyDto.getMemberId());
		apiKey.setExchange(apiKeyDto.getExchange());
		apiKey.setName(apiKeyDto.getName());
		apiKey.setAccessKey(apiKeyDto.getAccessKey());
		apiKey.setSecretKey(apiKeyDto.getSecretKey());

		return apiKey;
	}

	public ApiKeyDto toDto(@NotNull ApiKey apiKey) {
		Verifier.checkNullField(apiKey);

		ApiKeyDto apiKeyDto = new ApiKeyDto();

		apiKeyDto.setMemberId(apiKey.getMemberId());
		apiKeyDto.setExchange(apiKey.getExchange());
		apiKeyDto.setName(apiKey.getName());
		apiKeyDto.setAccessKey(apiKey.getAccessKey());
		apiKeyDto.setSecretKey(apiKey.getSecretKey());

		return apiKeyDto;
	}
}
