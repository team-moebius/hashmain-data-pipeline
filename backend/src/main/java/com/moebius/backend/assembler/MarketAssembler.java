package com.moebius.backend.assembler;

import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.markets.Market;
import com.moebius.backend.dto.TradeDto;
import com.moebius.backend.dto.exchange.upbit.UpbitTradeMetaDto;
import com.moebius.backend.dto.exchange.MarketsDto;
import com.moebius.backend.dto.frontend.response.MarketResponseDto;
import org.apache.commons.math3.util.Precision;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MarketAssembler {
	public Market toMarket(@NotNull Exchange exchange, @NotBlank String symbol) {
		Market market = new Market();
		market.setExchange(exchange);
		market.setSymbol(symbol);
		market.setCreatedAt(LocalDateTime.now());

		return market;
	}

	public List<Market> toMarkets(@NotNull Exchange exchange, @NotEmpty MarketsDto marketsDto) {
		return marketsDto.stream()
			.map(marketDto -> toMarket(exchange, marketDto.getMarket()))
			.collect(Collectors.toList());
	}

	public MarketResponseDto toResponseDto(Market market) {
		return MarketResponseDto.builder()
			.exchange(market.getExchange())
			.symbol(market.getSymbol())
			.currentPrice(market.getCurrentPrice())
			.changeRate(market.getChangeRate())
			.accumulatedTradePrice(market.getAccumulatedTradePrice())
			.build();
	}

	public Market assemble(Market market, TradeDto tradeDto, UpbitTradeMetaDto tradeMetaDto) {
		market.setCurrentPrice(tradeDto.getPrice());
		market.setChangeRate(Precision.round(tradeDto.getPrice() / tradeDto.getPrevClosingPrice() - 1, 4) * 100);
		market.setAccumulatedTradePrice(tradeMetaDto.getAccumulatedTradePrice());
		market.setAccumulatedTradeVolume(tradeMetaDto.getAccumulatedTradeVolume());
		market.setUpdatedAt(LocalDateTime.now());

		return market;
	}
}
