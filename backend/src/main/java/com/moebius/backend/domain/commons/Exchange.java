package com.moebius.backend.domain.commons;

import com.moebius.backend.exception.DataNotFoundException;
import com.moebius.backend.exception.ExceptionTypes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Exchange {
	UPBIT,
	BITHUMB,
	BINANCE;

	public static Exchange getBy(String name) {
		try {
			return Exchange.valueOf(name.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new DataNotFoundException(ExceptionTypes.WRONG_DATA.getMessage("[Order] Exchange(" + name + ")"));
		}
	}
}
