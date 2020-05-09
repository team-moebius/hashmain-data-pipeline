package com.moebius.backend.dto.exchange.upbit;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@ToString
public class UpbitOrderDto {
	@NotBlank
	private String market;
	@NotBlank
	private String side;
	@NotBlank
	private String ord_type;
	private String identifier;
	private Double volume;
	private Double price;
}
