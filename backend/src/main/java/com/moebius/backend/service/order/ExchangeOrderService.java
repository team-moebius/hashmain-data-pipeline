package com.moebius.backend.service.order;

import com.moebius.backend.domain.orders.Order;
import com.moebius.backend.domain.orders.OrderPosition;
import com.moebius.backend.domain.trades.TradeDocument;
import com.moebius.backend.service.exchange.ExchangeService;
import com.moebius.backend.service.exchange.ExchangeServiceFactory;
import com.moebius.backend.service.member.ApiKeyService;
import com.moebius.backend.utils.Verifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.util.Arrays;

import static com.moebius.backend.utils.ThreadScheduler.COMPUTE;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeOrderService {
	private final ApiKeyService apiKeyService;
	private final ExchangeServiceFactory exchangeServiceFactory;
	private final OrdersFactoryManager ordersFactoryManager;

	public void order(TradeDocument tradeDocument) {
		Verifier.checkNullFields(tradeDocument);

		ExchangeService exchangeService = exchangeServiceFactory.getService(tradeDocument.getExchange());

		Arrays.stream(OrderPosition.values())
			.forEach(orderPosition -> getOrders(orderPosition, tradeDocument)
				.subscribeOn(COMPUTE.scheduler())
				.map(order -> executeOrder(exchangeService, order))
				.subscribe());
	}

	private Flux<Order> getOrders(OrderPosition orderPosition, TradeDocument tradeDocument) {
		OrdersFactory ordersFactory = ordersFactoryManager.getOrdersFactory(orderPosition);

		if (ordersFactory != null) {
			return ordersFactory.getOrders(tradeDocument);
		}

		return Flux.empty();
	}

	private Disposable executeOrder(ExchangeService exchangeService, Order order) {
		return apiKeyService.getApiKeyById(order.getApiKeyId().toHexString())
			.flatMap(apiKey -> exchangeService.getAuthToken(apiKey.getAccessKey(), apiKey.getSecretKey()))
			.flatMap(authToken -> exchangeService.order(authToken, order))
			.subscribe();
	}
}
