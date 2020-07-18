package com.moebius.backend.assembler;

import com.moebius.backend.dto.exchange.AssetDto;
import com.moebius.backend.dto.frontend.response.AssetResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AssetAssembler {
	public AssetResponseDto assembleResponse(List<? extends AssetDto> assets) {
		return AssetResponseDto.builder()
			.assets(assets)
			.build();
	}

	public Map<String, AssetDto> assembleCurrencyAssets(List<? extends AssetDto> assets) {
		return assets.stream()
			.collect(Collectors.toMap(AssetDto::getCurrency, Function.identity()));
	}
}
