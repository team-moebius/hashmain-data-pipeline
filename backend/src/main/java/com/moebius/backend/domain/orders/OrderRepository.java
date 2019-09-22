package com.moebius.backend.domain.orders;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<Order, ObjectId> {
	@Query(value = "{ 'apiKey.id' : ?0 }")
	Flux<Order> findAllByApiKeyId(ObjectId apiKeyId);
}
