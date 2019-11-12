package com.moebius.backend.domain.orders;

import com.moebius.backend.domain.commons.Exchange;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {
	private final ReactiveMongoTemplate mongoTemplate;

	@Override
	public Flux<Order> findAndUpdateAllByAskCondition(Exchange exchange, String symbol, OrderPosition orderPosition, double price) {
		Query query = new Query(Criteria.where("price").lte(price)
			.and("symbol").is(symbol)
			.and("orderPosition").is(orderPosition)
			.and("orderStatus").is(OrderStatus.READY)
			.and("exchange").is(exchange));

		return mongoTemplate.find(query, Order.class)
			.flatMap(this::updateOrderStatusToExecuted);
	}

	@Override
	public Flux<Order> findAndUpdateAllByBidCondition(Exchange exchange, String symbol, OrderPosition orderPosition, double price) {
		Query query = new Query(Criteria.where("price").gte(price)
			.and("symbol").is(symbol)
			.and("orderPosition").is(orderPosition)
			.and("orderStatus").is(OrderStatus.READY)
			.and("exchange").is(exchange));

		return mongoTemplate.find(query, Order.class)
			.flatMap(this::updateOrderStatusToExecuted);
	}

	private Mono<Order> updateOrderStatusToExecuted(Order order) {
		order.setOrderStatus(OrderStatus.EXECUTED);
		return mongoTemplate.save(order);
	}
}
