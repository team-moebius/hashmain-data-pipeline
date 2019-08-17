package com.moebius.backend.domain.markets;

import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.commons.Symbol;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MarketRepository extends ReactiveMongoRepository<Market, ObjectId> {
	Flux<Market> findAllByExchange(Exchange exchange);

	Mono<Market> findByExchangeAndSymbol(Exchange exchange, Symbol symbol);
}
