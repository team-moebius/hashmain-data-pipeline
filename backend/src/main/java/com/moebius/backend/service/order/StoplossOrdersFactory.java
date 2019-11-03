package com.moebius.backend.service.order;

import com.moebius.backend.domain.orders.Order;
import com.moebius.backend.domain.orders.OrderPosition;
import com.moebius.backend.domain.orders.OrderRepository;
import com.moebius.backend.domain.trades.TradeDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import static com.moebius.backend.utils.ThreadScheduler.IO;
import static com.moebius.backend.utils.ThreadScheduler.COMPUTE;

@Component
@RequiredArgsConstructor
public class StoplossOrdersFactory implements OrdersFactory {
	private final OrderRepository orderRepository;

	@Override
	public OrderPosition getPosition() {
		return OrderPosition.STOPLOSS;
	}

	@Override
	public Flux<Order> gerOrders(TradeDocument tradeDocument) {
		return orderRepository.findAllByAskCondition(tradeDocument.getExchange(), tradeDocument.getSymbol(), OrderPosition.STOPLOSS, tradeDocument.getPrice())
			.publishOn(IO.scheduler())
			.subscribeOn(COMPUTE.scheduler());
	}
}
