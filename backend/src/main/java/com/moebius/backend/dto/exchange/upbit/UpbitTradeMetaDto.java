package com.moebius.backend.dto.exchange.upbit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@JsonDeserialize(builder = UpbitTradeMetaDto.UpbitTradeMetaDtoBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpbitTradeMetaDto {
	@JsonProperty("accTradePrice")
	private double accumulatedTradePrice;
	@JsonProperty("accTradeVolume")
	private double accumulatedTradeVolume;
}
