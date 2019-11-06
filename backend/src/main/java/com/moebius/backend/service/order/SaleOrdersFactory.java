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
public class SaleOrdersFactory implements OrdersFactory {
	private final OrderRepository orderRepository;

	@Override
	public OrderPosition getPosition() {
		return OrderPosition.SALE;
	}

	@Override
	public Flux<Order> gerOrders(TradeDocument tradeDocument) {
		return orderRepository.findAndUpdateAllByAskCondition(tradeDocument.getExchange(), tradeDocument.getSymbol(), OrderPosition.SALE, tradeDocument.getPrice())
			.publishOn(IO.scheduler())
			.subscribeOn(COMPUTE.scheduler());
	}
}
