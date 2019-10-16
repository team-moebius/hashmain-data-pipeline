package com.moebius.backend.dto.frontend.response;

import com.moebius.backend.dto.AssetDto;
import com.moebius.backend.dto.OrderDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderResponseDto {
	private List<OrderDto> orders;
	@ApiModelProperty(notes = "트레이더의 잔고 정보, 주문 갱신/생성/삭제시에는 Response로 가지고 오지 않는다.")
	private List<AssetDto> assets;
}