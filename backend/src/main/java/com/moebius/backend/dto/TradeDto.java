package com.moebius.backend.dto;

import com.moebius.backend.domain.commons.Exchange;
import lombok.Data;

@Data
public class TradeDto {
	private Exchange exchange;
	private String symbol;
	private double price;
}
