package com.moebius.backend.assembler;

import com.moebius.backend.domain.apikeys.ApiKey;
import com.moebius.backend.dto.frontend.ApiKeyDto;
import com.moebius.backend.dto.frontend.response.ApiKeyResponseDto;
import com.moebius.backend.utils.Verifier;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
public class ApiKeyAssembler {
	public ApiKey toApiKey(@NotNull ApiKeyDto apiKeyDto, @NotBlank String memberId) {
		Verifier.checkNullFields(apiKeyDto);

		ApiKey apiKey = new ApiKey();
		apiKey.setMemberId(new ObjectId(memberId));
		apiKey.setExchange(apiKeyDto.getExchange());
		apiKey.setName(apiKeyDto.getName());
		apiKey.setAccessKey(apiKeyDto.getAccessKey());
		apiKey.setSecretKey(apiKeyDto.getSecretKey());

		return apiKey;
	}

	public ApiKeyResponseDto toResponseDto(@NotNull ApiKey apiKey) {
		Verifier.checkNullFields(apiKey);

		ApiKeyResponseDto apiKeyResponseDto = new ApiKeyResponseDto();

		apiKeyResponseDto.setId(apiKey.getId().toHexString());
		apiKeyResponseDto.setExchange(apiKey.getExchange());
		apiKeyResponseDto.setName(apiKey.getName());
		apiKeyResponseDto.setAccessKey(apiKey.getAccessKey());
		apiKeyResponseDto.setSecretKey(apiKey.getSecretKey());

		return apiKeyResponseDto;
	}
}