package com.moebius.backend.domain.markets;

import com.moebius.backend.dto.TradeDto;
import reactor.core.publisher.Mono;

public interface MarketRepositoryCustom {
	Mono<Market> findAndUpdateOneByTrade(TradeDto tradeDto);
}
