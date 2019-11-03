package com.moebius.backend.domain.orders;

import com.moebius.backend.domain.commons.Exchange;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<Order, ObjectId> {
	Flux<Order> findAllByApiKeyId(ObjectId apiKeyId);

	@Query("{ 'exchange': ?0, 'symbol': ?1, 'orderPosition': ?2, 'price': { $lte: ?3 } }")
	Flux<Order> findAllByAskCondition(Exchange exchange, String symbol, OrderPosition orderPosition, double price);

	@Query("{ 'exchange': ?0, 'symbol': ?1, 'orderPosition': ?2, 'price': { $gte: ?3 } }")
	Flux<Order> findAllByBidCondition(Exchange exchange, String symbol, OrderPosition orderPosition, double price);
}
