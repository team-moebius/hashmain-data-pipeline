package com.moebius.backend.service.order;

import com.moebius.backend.domain.orders.Order;
import com.moebius.backend.domain.orders.OrderPosition;
import com.moebius.backend.dto.TradeDto;
import com.moebius.backend.service.exchange.ExchangeService;
import com.moebius.backend.service.exchange.ExchangeServiceFactory;
import com.moebius.backend.service.member.ApiKeyService;
import com.moebius.backend.service.order.factory.OrderFactoryManager;
import com.moebius.backend.utils.Verifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
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
	private final OrderCacheService orderCacheService;
	private final ExchangeServiceFactory exchangeServiceFactory;
	private final OrderFactoryManager orderFactoryManager;
	private final TransactionalOperator transactionalOperator;

	public void order(TradeDto tradeDto) {
		Verifier.checkNullFields(tradeDto);

		internalOrderService.findOrderCountByTradeDto(tradeDto)
			.filter(orderCount -> orderCount == 0)
			.switchIfEmpty(Mono.defer(() -> processTransactionalOrder(tradeDto)))
			.subscribe();
	}

	// TODO : Arrange this.
	public void updateOrderStatusWhenInProgress(TradeDto tradeDto) {
		Verifier.checkNullFields(tradeDto);

		ExchangeService exchangeService = exchangeServiceFactory.getService(tradeDto.getExchange());
		exchangeService.getUpdatedOrderStatus()
		return internalOrderService.findInProgressOrders(tradeDto)
			.map(order -> apiKeyService.getApiKeyById(order.getApiKeyId().toHexString()))

	}

	private Mono<Long> processTransactionalOrder(TradeDto tradeDto) {
		ExchangeService exchangeService = exchangeServiceFactory.getService(tradeDto.getExchange());

		return getAndUpdateOrders(tradeDto)
			.flatMap(order -> requestOrder(exchangeService, order))
			.count()
			.flatMap(count -> evictIfCountNotZero(tradeDto, count))
			.as(transactionalOperator::transactional)
			.onErrorReturn(UncategorizedMongoDbException.class, 0L);
	}

	private Flux<Order> getAndUpdateOrders(TradeDto tradeDto) {
		return Flux.concat(
			Flux.fromStream(Arrays.stream(OrderPosition.values())
				.map(orderFactoryManager::getOrdersFactory)
				.filter(Objects::nonNull)
				.map(ordersFactory -> ordersFactory.getAndUpdateOrdersToDone(tradeDto))
			)
		);
	}

	private Mono<ClientResponse> requestOrder(ExchangeService exchangeService, Order order) {
		return apiKeyService.getApiKeyById(order.getApiKeyId().toHexString())
			.flatMap(apiKey -> exchangeService.getAuthToken(apiKey.getAccessKey(), apiKey.getSecretKey()))
			.flatMap(authToken -> exchangeService.order(authToken, order));
	}

	private Mono<Long> evictIfCountNotZero(TradeDto tradeDto, long count) {
		if (count != 0) {
			orderCacheService.evictOrderCount(tradeDto.getExchange(), tradeDto.getSymbol());
		}
		return Mono.just(count);
	}
}
