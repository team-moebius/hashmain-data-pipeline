package com.moebius.backend.assembler;

import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.trades.Trade;
import com.moebius.backend.dto.exchange.TradeDto;
import com.moebius.backend.utils.Verifier;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Component
public class TradeAssembler {
	public Trade toTrade(@NotNull TradeDto tradeDto) {
		Verifier.checkNullField(tradeDto);

		Trade trade = new Trade();
		trade.setExchange(tradeDto.getExchange() == null ? Exchange.UPBIT : tradeDto.getExchange());
		trade.setSymbol(tradeDto.getSymbol());
		trade.setTradeType(tradeDto.getAskBid());
		trade.setChange(tradeDto.getChange());
		trade.setPrice(tradeDto.getTradePrice());
		trade.setVolume(tradeDto.getTradeVolume());
		trade.setPrevClosingPrice(tradeDto.getPrevClosingPrice());
		trade.setChangePrice(tradeDto.getChangePrice());
		trade.setCreatedAt(LocalDateTime.now());
		trade.setUpdatedAt(LocalDateTime.now());

		return trade;
	}

	public Trade toUpdatedTrade(@NotNull Trade trade, @NotNull TradeDto newTradeDto) {
		Verifier.checkNullField(newTradeDto);

		trade.setTradeType(newTradeDto.getAskBid());
		trade.setChange(newTradeDto.getChange());
		trade.setPrice(newTradeDto.getTradePrice());
		trade.setVolume(newTradeDto.getTradeVolume());
		trade.setPrevClosingPrice(newTradeDto.getPrevClosingPrice());
		trade.setChangePrice(newTradeDto.getChangePrice());
		trade.setUpdatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(newTradeDto.getTradeTimestamp()), TimeZone.getDefault().toZoneId()));

		return trade;
	}
}
