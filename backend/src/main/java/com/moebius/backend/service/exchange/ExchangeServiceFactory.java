package com.moebius.backend.service.exchange;

import com.moebius.backend.domain.commons.Exchange;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ExchangeServiceFactory {
	private final Map<Exchange, ExchangeService> exchangeServiceMap;

	public ExchangeServiceFactory(List<ExchangeService> exchangeServices) {
		this.exchangeServiceMap = exchangeServices.stream()
			.collect(Collectors.toMap(ExchangeService::getExchange, Function.identity()));
	}

	public ExchangeService getService(Exchange exchange) {
		return exchangeServiceMap.get(exchange);
	}
}
