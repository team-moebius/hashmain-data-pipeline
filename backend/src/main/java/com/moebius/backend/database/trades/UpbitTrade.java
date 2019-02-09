package com.moebius.backend.database.trades;

import com.moebius.backend.database.Base;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "upbit_trades")
@Getter
@Setter
public class UpbitTrade extends Base implements Trade {
	private String market;
	private double tradePrice;
	private double tradeVolume;
	private double prevClosingPrice;
	private String change;
	private double changePrice;
	private Type askBid;

	@Override
	public Exchange getExchange() {
		return Exchange.UPBIT;
	}

	@Override
	public String getMarketCode() {
		return market;
	}

	@Override
	public double getPrice() {
		return tradePrice;
	}

	@Override
	public double getVolume() {
		return tradeVolume;
	}
}
