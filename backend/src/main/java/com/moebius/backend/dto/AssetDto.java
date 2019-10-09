package com.moebius.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssetDto {
	@ApiModelProperty(notes = "화폐를 의미하는 영문 대문자 코드(KRW, BTC, ETH ...)")
	private String currency;
	@ApiModelProperty(notes = "주문가능 금액/수량")
	private String balance;
	@ApiModelProperty(notes = "주문 중 묶여있는(거래 대기중인) 금액/수량")
	private String locked;
}
