package com.moebius.batch.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpbitCoin extends Coin {
	private String authToken;

	@Override
	String getOwner() {
		return authToken;
	}

	@Override
	Class<?> getConcreteClass() {
		return UpbitCoin.class;
	}
}
