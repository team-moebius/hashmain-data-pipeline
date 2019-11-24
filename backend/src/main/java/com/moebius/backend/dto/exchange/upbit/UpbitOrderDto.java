package com.moebius.backend.dto.exchange.upbit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@ToString
public class UpbitOrderDto {
	@JsonProperty("identifier")
	private String id;
	@NotBlank
	@JsonProperty("market")
	private String symbol;
	@NotBlank
	@JsonProperty("side")
	private String orderPosition;
	@NotBlank
	@JsonProperty("ord_type")
	private String orderType;
	@PositiveOrZero
	private double price;
	@PositiveOrZero
	private double volume;
}
