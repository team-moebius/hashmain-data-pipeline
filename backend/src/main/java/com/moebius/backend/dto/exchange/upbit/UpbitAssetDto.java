package com.moebius.backend.dto.exchange.upbit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.moebius.backend.dto.exchange.AssetDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@JsonDeserialize(builder = UpbitAssetDto.UpbitAssetDtoBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpbitAssetDto implements AssetDto {
	@ApiModelProperty(notes = "화폐를 의미하는 영문 대문자 코드(KRW, BTC, ETH ...)")
	private String currency;
	@ApiModelProperty(notes = "주문가능 금액/수량")
	private String balance;
	@ApiModelProperty(notes = "주문 대기중인 금액/수량")
	private String locked;
	@JsonProperty("avg_buy_price")
	@ApiModelProperty(notes = "매수 평균가")
	private String averagePurchasePrice;
	@JsonProperty("avg_buy_price_modified")
	@ApiModelProperty(notes = "매수 평균가 수정 여부")
	private boolean averagePurchasePriceModified;

	@Override
	public boolean getAveragePurchasePriceModified() {
		return averagePurchasePriceModified;
	}
}
