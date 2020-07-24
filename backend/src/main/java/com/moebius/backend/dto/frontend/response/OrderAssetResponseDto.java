package com.moebius.backend.dto.frontend.response;

import com.moebius.backend.dto.OrderAssetDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class OrderAssetResponseDto {
	private List<OrderAssetDto> orderAssets;
}
