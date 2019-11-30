package com.moebius.backend.domain.orders;

import com.moebius.backend.domain.commons.Exchange;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface OrderRepository extends ReactiveMongoRepository<Order, ObjectId>, OrderRepositoryCustom {
	Flux<Order> findAllByApiKeyId(ObjectId apiKeyId);

	Flux<Order> countAllBySymbolAndOrderStatusAndExchange(String symbol, OrderStatus orderStatus, Exchange exchange);
}
