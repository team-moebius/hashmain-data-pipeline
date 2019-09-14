package com.moebius.backend.assembler;

import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.markets.Market;
import com.moebius.backend.dto.MarketDto;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class MarketAssembler {
	public Market toMarket(@NotNull MarketDto marketDto) {
		Market market = new Market();
		market.setExchange(marketDto.getExchange());
		market.setSymbol(marketDto.getSymbol());

		return market;
	}

	public Market toMarket(@NotNull Exchange exchange, String symbol) {
		Market market = new Market();
		market.setExchange(exchange);
		market.setSymbol(symbol);

		return market;
	}
}
