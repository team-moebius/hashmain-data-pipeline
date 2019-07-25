package com.moebius.backend.assembler;

import com.moebius.backend.domain.apikeys.ApiKey;
import com.moebius.backend.domain.stoplosses.Stoploss;
import com.moebius.backend.dto.frontend.StoplossDto;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Component
public class StoplossAssembler {
	public List<Stoploss> toStoplosses(@NotNull ApiKey apiKey, @NotNull List<StoplossDto> stoplossDtos) {
		List<Stoploss> stoplosses = new ArrayList<>();
		stoplossDtos.forEach(dto -> {
			Stoploss stoploss = new Stoploss();
			stoploss.setApiKey(apiKey);
			stoploss.setExchange(dto.getExchange());
			stoploss.setSymbol(dto.getSymbol());
			stoploss.setTradeType(dto.getTradeType());
			stoploss.setOrderType(dto.getOrderType());
			stoploss.setPrice(dto.getPrice());
			stoploss.setVolume(dto.getVolume());
			stoplosses.add(stoploss);
		});
		return stoplosses;
	}

	public StoplossDto toDto(@NotNull Stoploss stoploss) {
		StoplossDto dto = new StoplossDto();
		dto.setExchange(stoploss.getExchange());
		dto.setSymbol(stoploss.getSymbol());
		dto.setTradeType(stoploss.getTradeType());
		dto.setOrderType(stoploss.getOrderType());
		dto.setPrice(stoploss.getPrice());
		dto.setVolume(stoploss.getVolume());
		return dto;
	}
}