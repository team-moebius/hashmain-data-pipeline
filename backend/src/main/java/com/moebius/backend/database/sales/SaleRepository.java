package com.moebius.backend.database.sales;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SaleRepository extends ReactiveMongoRepository<Sale, ObjectId> {
}
