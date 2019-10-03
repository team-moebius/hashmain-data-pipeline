package com.moebius.backend.service.order;

import com.moebius.backend.assembler.OrderAssembler;
import com.moebius.backend.domain.commons.EventType;
import com.moebius.backend.domain.orders.Order;
import com.moebius.backend.domain.orders.OrderRepository;
import com.moebius.backend.dto.OrderDto;
import com.moebius.backend.dto.TradeDto;
import com.moebius.backend.dto.frontend.response.OrderResponseDto;
import com.moebius.backend.exception.DataNotFoundException;
import com.moebius.backend.exception.ExceptionTypes;
import com.moebius.backend.service.member.ApiKeyService;
import com.moebius.backend.utils.Verifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static com.moebius.backend.utils.ThreadScheduler.COMPUTE;
import static com.moebius.backend.utils.ThreadScheduler.IO;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final OrderAssembler orderAssembler;
	private final ApiKeyService apiKeyService;

	public Mono<ResponseEntity<List<OrderResponseDto>>> upsertOrders(String apiKeyId, List<OrderDto> orderDtos) {
		return apiKeyService.getApiKeyById(apiKeyId)
			.subscribeOn(COMPUTE.scheduler())
			.switchIfEmpty(Mono.defer(
				() -> Mono.error(new DataNotFoundException(ExceptionTypes.NONEXISTENT_DATA.getMessage("[ApiKey] " + apiKeyId)))))
			.flatMapIterable(apiKey -> orderAssembler.toOrders(apiKey, orderDtos))
			.flatMap(this::processOrder)
			.collectList()
			.map(ResponseEntity::ok);
	}

	public Mono<ResponseEntity<List<OrderResponseDto>>> getOrdersByApiKey(String apiKeyId) {
		return orderRepository.findAllByApiKeyId(new ObjectId(apiKeyId))
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.switchIfEmpty(Mono.defer(() -> Mono.error(new DataNotFoundException(
				ExceptionTypes.NONEXISTENT_DATA.getMessage("[Order] Order by apiKeyId(" + apiKeyId + ")")))))
			.map(order -> orderAssembler.toResponseDto(order, EventType.READ))
			.collectList()
			.map(ResponseEntity::ok);
	}

	private Flux<OrderDto> getOrdersByTrade(TradeDto tradeDto) {
		Verifier.checkNullFields(tradeDto);

		return orderRepository.findAllByExchangeAndSymbol(tradeDto.getExchange(), tradeDto.getSymbol())
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.switchIfEmpty(Mono.defer(() -> Mono.error(new DataNotFoundException(
				ExceptionTypes.NONEXISTENT_DATA.getMessage("[Order] Order by trade(" + tradeDto + ")")))))
			.map(order -> orderAssembler.toOrderDto(order, EventType.READ));
	}

	private Mono<OrderResponseDto> processOrder(Order order) {
		return Objects.isNull(order.getId()) ? createOrder(order) : deleteOrder(order);
	}

	private Mono<OrderResponseDto> createOrder(Order order) {
		return orderRepository.save(order)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.map(createdOrder -> orderAssembler.toResponseDto(createdOrder, EventType.CREATE));
	}

	private Mono<OrderResponseDto> deleteOrder(Order order) {
		return orderRepository.delete(order)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.map(aVoid -> orderAssembler.toResponseDto(order, EventType.DELETE));
	}
}
