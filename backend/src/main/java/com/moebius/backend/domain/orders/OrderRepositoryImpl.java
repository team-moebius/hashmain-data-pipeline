package com.moebius.backend.domain.orders;

import com.moebius.backend.domain.commons.Exchange;
import com.mongodb.ReadPreference;
import com.mongodb.reactivestreams.client.MongoClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {
	private final ReactiveMongoTemplate mongoTemplate;
	private final MongoClient mongoClient;

	@Override
	public Flux<Order> findAndUpdateAllByAskCondition(Exchange exchange, String symbol, OrderPosition orderPosition, double price) {
		Query query = new Query(Criteria.where("price").lte(price)
			.and("symbol").is(symbol)
			.and("orderPosition").is(orderPosition)
			.and("orderStatus").is(OrderStatus.READY)
			.and("exchange").is(exchange));

		return executeTransactionalQuery(query);
	}

	@Override
	public Flux<Order> findAndUpdateAllByBidCondition(Exchange exchange, String symbol, OrderPosition orderPosition, double price) {
		Query query = new Query(Criteria.where("price").gte(price)
			.and("symbol").is(symbol)
			.and("orderPosition").is(orderPosition)
			.and("orderStatus").is(OrderStatus.READY)
			.and("exchange").is(exchange));

		return executeTransactionalQuery(query);
	}

	private Flux<Order> executeTransactionalQuery(Query query) {
		mongoTemplate.setReadPreference(ReadPreference.primary());
		return mongoTemplate.inTransaction(mongoClient.startSession())
			.execute(operations -> operations.find(query, Order.class)
				.flatMap(this::updateOrderStatusToExecuted))
			.doOnTerminate(() -> mongoTemplate.setReadPreference(ReadPreference.secondary()));
	}

	private Mono<Order> updateOrderStatusToExecuted(Order order) {
		order.setOrderStatus(OrderStatus.EXECUTED);
		return mongoTemplate.save(order);
	}
}
