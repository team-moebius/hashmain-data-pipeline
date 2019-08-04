package com.moebius.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moebius.backend.domain.commons.Change;
import com.moebius.backend.domain.commons.TradeType;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trade {
	@JsonProperty("ty")
	private String type;
	@JsonProperty("cd")
	private String symbol;
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
}
