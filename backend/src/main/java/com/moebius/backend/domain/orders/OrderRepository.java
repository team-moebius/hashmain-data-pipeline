package com.moebius.backend.domain.orders;

import com.moebius.backend.domain.commons.Exchange;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderRepository extends ReactiveMongoRepository<Order, ObjectId>, OrderRepositoryCustom {
	Flux<Order> findAllByApiKeyId(ObjectId apiKeyId);

	Mono<Long> countBySymbolAndExchangeAndOrderStatus(String symbol, Exchange exchange, OrderStatus orderStatus);
}
