package com.moebius.backend.dto.exchange;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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
