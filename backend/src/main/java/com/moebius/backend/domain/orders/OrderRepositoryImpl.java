package com.moebius.backend.domain.orders;

import com.moebius.backend.domain.commons.Exchange;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {
	private final ReactiveMongoTemplate mongoTemplate;

	@Override
	public Flux<Order> findAndUpdateAllByAskCondition(Exchange exchange, String symbol, OrderPosition orderPosition, double price) {
		return null;
	}

	@Override
	public Flux<Order> findAndUpdateAllByBidCondition(Exchange exchange, String symbol, OrderPosition orderPosition, double price) {
		return null;
	}
}
