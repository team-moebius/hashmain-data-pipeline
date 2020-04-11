package com.moebius.backend.dto.exchange;

public interface AssetDto {
	String getCurrency();

	double getBalance();

	double getLocked();

	double getAveragePurchasePrice();

	default boolean getAveragePurchasePriceModified() {
		return false;
	}
}
