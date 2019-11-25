package com.moebius.backend.service.order;

import com.moebius.backend.domain.orders.Order;
import com.moebius.backend.domain.orders.OrderPosition;
import com.moebius.backend.domain.orders.OrderRepository;
import com.moebius.backend.dto.TradeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import static com.moebius.backend.utils.ThreadScheduler.COMPUTE;
import static com.moebius.backend.utils.ThreadScheduler.IO;

@Slf4j
@Component
@RequiredArgsConstructor
public class StoplossOrdersFactory implements OrdersFactory {
	private final OrderRepository orderRepository;

	@Override
	public OrderPosition getPosition() {
		return OrderPosition.STOPLOSS;
	}

	@Override
	public Flux<Order> getOrders(TradeDto tradeDto) {
		return orderRepository.findAndUpdateAllByBidCondition(tradeDto.getExchange(), tradeDto.getSymbol(), OrderPosition.STOPLOSS, tradeDto.getPrice())
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler());
	}
}
