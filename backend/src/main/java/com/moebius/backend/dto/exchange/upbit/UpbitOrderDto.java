package com.moebius.backend.dto.exchange.upbit;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class UpbitOrderDto {
	private String identifier;
	@NotBlank
	private String market;
	@NotBlank
	private String side;
	@NotBlank
	private String ord_type;
	private Double price;
	private Double volume;
}
