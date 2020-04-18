package com.moebius.backend.domain.orders;

import com.moebius.backend.domain.commons.Exchange;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderRepository extends ReactiveMongoRepository<Order, ObjectId>, OrderRepositoryCustom {
	Flux<Order> findAllByApiKeyId(ObjectId apiKeyId);

	Flux<Order> findAllByApiKeyIdAndOrderStatusNot(ObjectId apiKeyId, OrderStatus orderStatus);

	Mono<Long> countBySymbolAndExchangeAndOrderStatus(String symbol, Exchange exchange, OrderStatus orderStatus);
}
