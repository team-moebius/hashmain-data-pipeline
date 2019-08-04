package com.moebius.backend.domain.stoplosses;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface StoplossRepository extends ReactiveMongoRepository<Stoploss, ObjectId> {
	@Query(value = "{ 'apiKey.id' : ?0 }")
	Flux<Stoploss> findAllByApiKeyId(ObjectId apiKeyId);
}
