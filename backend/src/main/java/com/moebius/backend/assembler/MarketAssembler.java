package com.moebius.backend.assembler;

import com.moebius.backend.domain.markets.Market;
import com.moebius.backend.domain.stoplosses.Stoploss;
import com.moebius.backend.dto.MarketDto;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class MarketAssembler {
	public Market toMarketFromStoploss(@NotNull Stoploss stoploss) {
		Market market = new Market();
		market.setExchange(stoploss.getExchange());
		market.setSymbol(stoploss.getSymbol());
		market.setActive(true);

		return market;
	}

	public Market toMarket(@NotNull MarketDto marketDto) {
		Market market = new Market();
		market.setExchange(marketDto.getExchange());
		market.setSymbol(marketDto.getSymbol());
		market.setActive(marketDto.isActive());

		return market;
	}
}
