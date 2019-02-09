package com.moebius.backend.database.trades;

public interface Trade {
	Exchange getExchange();

	String getMarketCode();

	double getPrice();

	double getVolume();
}
