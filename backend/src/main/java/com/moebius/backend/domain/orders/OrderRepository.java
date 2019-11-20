package com.moebius.backend.domain.orders;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface OrderRepository extends ReactiveMongoRepository<Order, ObjectId>, OrderRepositoryCustom {
	Flux<Order> findAllByApiKeyId(ObjectId apiKeyId);
}
