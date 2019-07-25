package com.moebius.backend.domain.trades;

import com.moebius.backend.domain.commons.Symbol;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TradeRepository extends ReactiveMongoRepository<Trade, ObjectId> {
	Mono<Trade> findBySymbol(Symbol symbol);
}
