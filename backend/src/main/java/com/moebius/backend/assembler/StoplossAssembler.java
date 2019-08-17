package com.moebius.backend.assembler;

import com.moebius.backend.domain.apikeys.ApiKey;
import com.moebius.backend.domain.stoplosses.Stoploss;
import com.moebius.backend.dto.frontend.StoplossDto;
import com.moebius.backend.dto.frontend.response.StoplossResponseDto;
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
			stoploss.setApiKeyId(apiKey.getId());
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

	public StoplossResponseDto toRespoonseDto(@NotNull Stoploss stoploss) {
		StoplossResponseDto responseDto = new StoplossResponseDto();
		responseDto.setId(stoploss.getId().toHexString());
		responseDto.setExchange(stoploss.getExchange());
		responseDto.setSymbol(stoploss.getSymbol());
		responseDto.setTradeType(stoploss.getTradeType());
		responseDto.setOrderType(stoploss.getOrderType());
		responseDto.setPrice(stoploss.getPrice());
		responseDto.setVolume(stoploss.getVolume());

		return responseDto;
	}
}