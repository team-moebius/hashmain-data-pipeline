package com.moebius.backend.service.order;

import com.moebius.backend.domain.orders.Order;
import com.moebius.backend.domain.orders.OrderPosition;
import com.moebius.backend.domain.orders.OrderStatus;
import com.moebius.backend.dto.TradeDto;
import com.moebius.backend.service.exchange.ExchangeService;
import com.moebius.backend.service.exchange.ExchangeServiceFactory;
import com.moebius.backend.service.member.ApiKeyService;
import com.moebius.backend.utils.Verifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeOrderService {
	private final ApiKeyService apiKeyService;
	private final InternalOrderService internalOrderService;
	private final ExchangeServiceFactory exchangeServiceFactory;
	private final OrdersFactoryManager ordersFactoryManager;

	public void order(TradeDto tradeDto) {
		Verifier.checkNullFields(tradeDto);

		internalOrderService.findOrderCountByTradeDto(tradeDto)
			.filter(orderCount -> {
				if (orderCount != 0) {
					log.info("[Order] {}/{}/{} : {}", tradeDto.getExchange(), tradeDto.getSymbol(), OrderStatus.READY, orderCount);
				}
				return orderCount == 0;
			})
			.switchIfEmpty(Mono.defer(() -> execute(tradeDto)))
			.subscribe();
	}

	private Mono<Long> execute(TradeDto tradeDto) {
		ExchangeService exchangeService = exchangeServiceFactory.getService(tradeDto.getExchange());

		return getAndUpdateOrders(tradeDto)
			.flatMap(order -> executeOrder(exchangeService, order))
			.count()
			.doOnSuccess(count -> evictIfCountNotZero(tradeDto, count));
	}

	private Flux<Order> getAndUpdateOrders(TradeDto tradeDto) {
		return Flux.concat(
			Flux.fromStream(Arrays.stream(OrderPosition.values())
				.map(ordersFactoryManager::getOrdersFactory)
				.filter(Objects::nonNull)
				.map(ordersFactory -> ordersFactory.getAndUpdateOrders(tradeDto))
			)
		);
	}

	private Mono<ClientResponse> executeOrder(ExchangeService exchangeService, Order order) {
		return apiKeyService.getApiKeyById(order.getApiKeyId().toHexString())
			.flatMap(apiKey -> exchangeService.getAuthToken(apiKey.getAccessKey(), apiKey.getSecretKey()))
			.flatMap(authToken -> exchangeService.order(authToken, order));
	}

	private void evictIfCountNotZero(TradeDto tradeDto, long count) {
		if (count != 0) {
			internalOrderService.evictOrderCount(tradeDto);
		}
	}
}
