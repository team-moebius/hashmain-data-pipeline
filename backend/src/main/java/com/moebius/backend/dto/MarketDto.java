package com.moebius.backend.dto;

import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.commons.Symbol;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MarketDto {
	@NotNull
	private Exchange exchange;
	@NotNull
	private Symbol symbol;
	private boolean active;
}
