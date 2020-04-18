package com.moebius.backend.dto.frontend.response;

import com.moebius.backend.dto.exchange.AssetDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class AssetResponseDto {
	@ApiModelProperty(notes = "트레이더의 잔고 정보, 하나의 AssetDto 한 종류의 자산(KRW, BTC ...)을 의미한다.")
	private List<? extends AssetDto> assets;
}
