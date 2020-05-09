package com.moebius.backend.dto.exchange.upbit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * This external order dto's shape(field declaration order, type) MUST NOT be changed.
 */
@Getter
@Builder
@JsonDeserialize(builder = UpbitOrderDto.UpbitOrderDtoBuilder.class)
public class UpbitOrderDto {
	@NotBlank
	private String market;
	private Double volume;
	private String identifier;
	@NotBlank
	private String side;
	@NotBlank
	private String ord_type;
	private Double price;
}
