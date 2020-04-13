package com.moebius.backend.dto.exchange;

import org.apache.commons.lang3.StringUtils;

public final class EmptyAssetDto implements AssetDto {
	@Override
	public String getCurrency() {
		return StringUtils.EMPTY;
	}

	@Override
	public double getBalance() {
		return 0;
	}

	@Override
	public double getLocked() {
		return 0;
	}

	@Override
	public double getAveragePurchasePrice() {
		// The average purchase price should be 1 because it is used as a divisor when creating OrderStatusDto.
		return 1;
	}

	@Override
	public boolean getAveragePurchasePriceModified() {
		return false;
	}
}
