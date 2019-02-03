package com.moebius.batch.tracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Asset {
	private String currency;
	private String balance;
	private String locked;
	@JsonProperty("avg_krw_buy_price")
	private String avgKrwBuyPrice;
	private boolean modified;
}
