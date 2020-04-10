package com.moebius.backend.assembler;

import com.moebius.backend.dto.exchange.upbit.UpbitAssetDto;
import com.moebius.backend.dto.frontend.response.AssetResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssetAssembler {
	public AssetResponseDto toResponseDto(List<UpbitAssetDto> assets) {
		return AssetResponseDto.builder()
			.assets(assets)
			.build();
	}
}
