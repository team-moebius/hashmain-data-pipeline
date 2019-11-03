package com.moebius.backend.service.order;

import com.moebius.backend.domain.orders.Order;
import com.moebius.backend.domain.orders.OrderPosition;
import com.moebius.backend.domain.trades.TradeDocument;
import reactor.core.publisher.Flux;

public interface OrdersFactory {
	OrderPosition getPosition();

	Flux<Order> gerOrders(TradeDocument tradeDocument);
}
