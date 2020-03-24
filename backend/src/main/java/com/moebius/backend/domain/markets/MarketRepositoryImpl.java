package com.moebius.backend.domain.markets;

import com.moebius.backend.dto.TradeDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.util.Precision;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class MarketRepositoryImpl implements MarketRepositoryCustom {
	private final ReactiveMongoTemplate mongoTemplate;

	@Override
	public Mono<Market> findAndUpdateOneByTrade(TradeDto tradeDto) {
		Query query = new Query(Criteria.where("exchange").is(tradeDto.getExchange())
			.and("symbol").is(tradeDto.getSymbol()));

		return executeQuery(query, tradeDto);
	}

	private Mono<Market> executeQuery(Query query, TradeDto tradeDto) {
		return mongoTemplate.findOne(query, Market.class)
			.flatMap(market -> updateMarketPrice(market, tradeDto));
	}

	private Mono<Market> updateMarketPrice(Market market, TradeDto tradeDto) {
		market.setCurrentPrice(tradeDto.getPrice());
		market.setChangeRate(Precision.round(tradeDto.getPrice() / tradeDto.getPrevClosingPrice() - 1, 4));
		market.setUpdatedAt(LocalDateTime.now());
		return mongoTemplate.save(market);
	}
}
