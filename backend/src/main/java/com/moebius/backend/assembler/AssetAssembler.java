package com.moebius.backend.assembler;

import com.moebius.backend.dto.exchange.AssetDto;
import com.moebius.backend.dto.frontend.response.AssetResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssetAssembler {
	public AssetResponseDto toResponseDto(List<? extends AssetDto> assets) {
		return AssetResponseDto.builder()
			.assets(assets)
			.build();
	}
}
