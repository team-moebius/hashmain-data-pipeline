package com.moebius.backend.domain.purchases;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PurchaseRepository extends ReactiveMongoRepository<Purchase, ObjectId> {
}
