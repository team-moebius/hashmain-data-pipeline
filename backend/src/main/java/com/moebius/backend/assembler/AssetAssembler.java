package com.moebius.backend.assembler;

import com.moebius.backend.dto.AssetDto;
import com.moebius.backend.dto.frontend.response.AssetResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssetAssembler {
	public AssetResponseDto toResponseDto(List<AssetDto> assets) {
		return AssetResponseDto.builder()
			.assets(assets)
			.build();
	}
}
