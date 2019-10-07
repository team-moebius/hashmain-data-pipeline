package com.moebius.backend.service.order;

import com.moebius.backend.assembler.OrderAssembler;
import com.moebius.backend.domain.apikeys.ApiKey;
import com.moebius.backend.domain.commons.EventType;
import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.orders.Order;
import com.moebius.backend.domain.orders.OrderRepository;
import com.moebius.backend.dto.frontend.OrderDto;
import com.moebius.backend.dto.frontend.response.OrderResponseDto;
import com.moebius.backend.exception.DataNotFoundException;
import com.moebius.backend.exception.ExceptionTypes;
import com.moebius.backend.service.member.ApiKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
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
public class OrderService {
	private final OrderRepository orderRepository;
	private final OrderAssembler orderAssembler;
	private final ApiKeyService apiKeyService;

	public Mono<ResponseEntity<List<OrderResponseDto>>> processOrders(String memberId, List<OrderDto> orderDtos) {
		return apiKeyService.getApiKeyByMemberIdAndExchange(memberId, Exchange.UPBIT)
			.subscribeOn(COMPUTE.scheduler())
			.flatMapIterable(apiKey -> orderDtos.stream()
				.map(orderDto -> processOrder(apiKey, orderDto))
				.collect(Collectors.toList()))
			.collectList()
			.map(ResponseEntity::ok);
	}

	public Mono<ResponseEntity<List<OrderResponseDto>>> getOrdersByMemberId(String memberId) {
		return apiKeyService.getApiKeyByMemberIdAndExchange(memberId, Exchange.UPBIT)
			.map(apiKey -> orderRepository.findAllByApiKeyId(apiKey.getId()))
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.switchIfEmpty(Mono.defer(() -> Mono.error(new DataNotFoundException(
				ExceptionTypes.NONEXISTENT_DATA.getMessage("[Order] order information based on memberId(" + memberId + ")")))))
			.flatMap(orderFlux -> orderFlux.map(order -> orderAssembler.toResponseDto(order, EventType.READ))
				.collectList())
			.map(ResponseEntity::ok);
	}

	private OrderResponseDto processOrder(ApiKey apiKey, OrderDto orderDto) {
		EventType eventType = orderDto.getEventType();
		if (eventType == DELETE) {
			deleteOrder(orderDto.getId()).subscribe();
			log.info("deleted order.");
			return orderAssembler.toSimpleResponseDto(orderDto.getId(), DELETE);
		}

		Order order = orderAssembler.toOrder(apiKey, orderDto);
		upsertOrder(order).subscribe();
		log.info("updated order.");
		return orderAssembler.toResponseDto(order, eventType);
	}

	private Mono<Order> upsertOrder(Order order) {
		return orderRepository.save(order)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler());
	}

	private Mono<Void> deleteOrder(String id) {
		return orderRepository.deleteById(new ObjectId(id))
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler());
	}
}
