package com.moebius.backend.service.order;

import com.moebius.backend.assembler.OrderAssembler;
import com.moebius.backend.domain.apikeys.ApiKey;
import com.moebius.backend.domain.commons.EventType;
import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.orders.Order;
import com.moebius.backend.domain.orders.OrderRepository;
import com.moebius.backend.domain.orders.OrderStatus;
import com.moebius.backend.dto.AssetsDto;
import com.moebius.backend.dto.OrderDto;
import com.moebius.backend.dto.TradeDto;
import com.moebius.backend.dto.frontend.response.OrderResponseDto;
import com.moebius.backend.exception.DataNotFoundException;
import com.moebius.backend.exception.ExceptionTypes;
import com.moebius.backend.service.exchange.ExchangeServiceFactory;
import com.moebius.backend.service.member.ApiKeyService;
import com.moebius.backend.utils.Verifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import static com.moebius.backend.domain.commons.EventType.DELETE;
import static com.moebius.backend.utils.ThreadScheduler.COMPUTE;
import static com.moebius.backend.utils.ThreadScheduler.IO;

@Slf4j
@Service
@RequiredArgsConstructor
public class InternalOrderService {
	private final OrderRepository orderRepository;
	private final OrderAssembler orderAssembler;
	private final ApiKeyService apiKeyService;
	private final OrderCacheService orderCacheService;
	private final ExchangeServiceFactory exchangeServiceFactory;

	public Mono<ResponseEntity<OrderResponseDto>> processOrders(String memberId, Exchange exchange, List<OrderDto> orderDtos) {
		return apiKeyService.getApiKeyByMemberIdAndExchange(memberId, exchange)
			.subscribeOn(COMPUTE.scheduler())
			.flatMapIterable(apiKey -> orderDtos.stream()
				.map(orderDto -> processOrder(apiKey, orderDto))
				.collect(Collectors.toList()))
			.collectList()
			.doOnSuccess(this::evictOrderCaches)
			.map(dtos -> orderAssembler.toResponseDto(dtos, null))
			.map(ResponseEntity::ok);
	}

	public Mono<ResponseEntity<OrderResponseDto>> getOrdersAndAssets(String memberId, Exchange exchange) {
		return Mono.zip(getOrdersByMemberIdAndExchange(memberId, exchange), getAssetsByMemberIdAndExchange(memberId, exchange))
			.subscribeOn(COMPUTE.scheduler())
			.map(tuple -> orderAssembler.toResponseDto(tuple.getT1(), tuple.getT2()))
			.map(ResponseEntity::ok);
	}

	// FIXME
	public Mono<ResponseEntity<OrderResponseDto>> getOrdersAndAssets(String memberId, Exchange exchange, String symbol) {
		return Mono.zip(getOrdersByMemberIdAndExchange(memberId, exchange), getAssetsByMemberIdAndExchange(memberId, exchange))
			.subscribeOn(COMPUTE.scheduler())
			.map(tuple -> orderAssembler.toResponseDto(tuple.getT1(), tuple.getT2()))
			.map(ResponseEntity::ok);
	}

	@Cacheable(value = "readyOrderCount", key = "{#tradeDto.exchange, #tradeDto.symbol, 'READY'}")
	public Mono<Long> findOrderCountByTradeDto(TradeDto tradeDto) {
		log.info("[Order] [{}/{}/{}] Start to get count of orders from repository because not found in cache.", tradeDto.getExchange(), tradeDto.getSymbol(), OrderStatus.READY);
		return orderRepository.countBySymbolAndOrderStatusAndExchange(tradeDto.getSymbol(), OrderStatus.READY, tradeDto.getExchange())
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.cache();
	}

	private Mono<List<OrderDto>> getOrdersByMemberIdAndExchange(String memberId, Exchange exchange) {
		return apiKeyService.getApiKeyByMemberIdAndExchange(memberId, exchange)
			.map(apiKey -> orderRepository.findAllByApiKeyId(apiKey.getId()))
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.switchIfEmpty(Mono.defer(() -> Mono.error(new DataNotFoundException(
				ExceptionTypes.NONEXISTENT_DATA.getMessage("[Order] order information based on memberId(" + memberId + ")")))))
			.flatMap(orders -> orders.map(order -> orderAssembler.toDto(order, EventType.READ))
				.collectList());
	}

	private Mono<AssetsDto> getAssetsByMemberIdAndExchange(String memberId, Exchange exchange) {
		return apiKeyService.getExchangeAuthToken(memberId, exchange)
			.flatMap(authToken -> exchangeServiceFactory.getService(exchange)
				.getAssets(authToken))
			.subscribeOn(COMPUTE.scheduler());
	}

	private OrderDto processOrder(ApiKey apiKey, OrderDto orderDto) {
		EventType eventType = orderDto.getEventType();
		if (eventType == DELETE) {
			deleteOrder(orderDto.getId()).subscribe();
		} else {
			Order order = orderAssembler.toOrder(apiKey, orderDto);
			saveOrder(order).subscribe();
		}
		return orderDto;
	}

	private Mono<Order> saveOrder(Order order) {
		return orderRepository.save(order)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler());
	}

	private Mono<Void> deleteOrder(String id) {
		Verifier.checkBlankString(id);

		return orderRepository.deleteById(new ObjectId(id))
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler());
	}

	private void evictOrderCaches(List<OrderDto> orderDtos) {
		orderDtos.stream()
			.collect(Collectors.groupingBy(OrderDto::getSymbol))
			.keySet()
			.forEach(symbol -> orderCacheService.evictOrderCount(orderDtos.get(0).getExchange(), symbol));
	}
}
