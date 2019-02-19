package com.moebius.backend.database.trades;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TradeRepository extends ReactiveMongoRepository<Trade, ObjectId> {
}
