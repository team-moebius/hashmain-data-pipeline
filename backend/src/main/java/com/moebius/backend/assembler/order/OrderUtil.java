package com.moebius.backend.assembler.order;

import org.springframework.stereotype.Component;

@Component
public class OrderUtil {
	private static String SEPARATOR = "_";
	private static int CURRENCY_INDEX = 1;

	public String getCurrencyBySymbol(String symbol) {
		return symbol.split(SEPARATOR)[CURRENCY_INDEX];
	}
}
