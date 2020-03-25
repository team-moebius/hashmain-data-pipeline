package com.moebius.backend.domain.markets;

import com.moebius.backend.domain.commons.Exchange;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MarketRepository extends ReactiveMongoRepository<Market, ObjectId> {
	Flux<Market> findAllByExchange(Exchange exchange);

	Mono<Market> findByExchangeAndSymbol(Exchange exchange, String symbol);

	Mono<Long> countByExchange(Exchange exchange);
}
