package com.moebius.backend.dto.exchange.upbit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

@Getter
@Builder(builderClassName = "UpbitOrderStatusDtoBuilder", toBuilder = true)
@ToString
@JsonDeserialize(builder = UpbitOrderStatusDto.UpbitOrderStatusDtoBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpbitOrderStatusDto {
	private String state;

	@JsonPOJOBuilder(withPrefix = "")
	public static class UpbitOrderStatusDtoBuilder { }
}
