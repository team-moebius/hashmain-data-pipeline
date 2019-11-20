package com.moebius.backend.domain.orders;

import com.moebius.backend.domain.commons.Exchange;
import reactor.core.publisher.Flux;

public interface OrderRepositoryCustom {
	Flux<Order> findAndUpdateAllByAskCondition(Exchange exchange, String symbol, OrderPosition orderPosition, double price);

	Flux<Order> findAndUpdateAllByBidCondition(Exchange exchange, String symbol, OrderPosition orderPosition, double price);
}
