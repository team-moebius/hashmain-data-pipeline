package com.moebius.backend.dto.exchange;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moebius.backend.domain.commons.Change;
import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.commons.Symbol;
import com.moebius.backend.domain.commons.TradeType;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeDto {
	@JsonProperty("ty")
	private String type;
	@JsonProperty("cd")
	private Symbol symbol;
	@JsonProperty("ttms")
	private long tradeTimestamp;
	@JsonProperty("tp")
	private double tradePrice;
	@JsonProperty("tv")
	private double tradeVolume;
	@JsonProperty("ab")
	private TradeType askBid;
	@JsonProperty("pcp")
	private double prevClosingPrice;
	@JsonProperty("c")
	private Change change;
	@JsonProperty("cp")
	private double changePrice;
	@JsonProperty("sid")
	private long sequentialId;

	private Exchange exchange;
}
