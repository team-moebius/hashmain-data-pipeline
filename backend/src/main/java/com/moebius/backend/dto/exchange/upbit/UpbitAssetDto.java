package com.moebius.backend.dto.exchange.upbit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.moebius.backend.dto.exchange.AssetDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.commons.lang3.math.NumberUtils;

@Builder(builderClassName = "UpbitAssetDtoBuilder", toBuilder = true)
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
	public String getCurrency() {
		return currency;
	}

	@Override
	public double getBalance() {
		return NumberUtils.toDouble(balance);
	}

	@Override
	public double getLocked() {
		return NumberUtils.toDouble(locked);
	}

	@Override
	public double getAveragePurchasePrice() {
		return NumberUtils.toDouble(averagePurchasePrice);
	}

	@Override
	public boolean getAveragePurchasePriceModified() {
		return averagePurchasePriceModified;
	}

	@JsonPOJOBuilder(withPrefix = "")
	public static class UpbitAssetDtoBuilder { }
}
