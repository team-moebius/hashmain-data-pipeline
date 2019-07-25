package com.moebius.backend.domain.apikeys;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyRepository extends ReactiveMongoRepository<ApiKey, ObjectId> {
}
