package com.moebius.backend.service.order;

import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.orders.Order;
import com.moebius.backend.domain.orders.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static com.moebius.backend.utils.ThreadScheduler.COMPUTE;
import static com.moebius.backend.utils.ThreadScheduler.IO;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeOrderService {
	private final OrderRepository orderRepository;

	public Flux<Order> getOrdersByExchangeAndSymbol(Exchange exchange, String symbol) {
		return orderRepository.findAllByExchangeAndSymbol(exchange, symbol)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler());
	}
}
