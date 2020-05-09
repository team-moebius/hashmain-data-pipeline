package com.moebius.backend.service.order;

import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.orders.OrderRepository;
import com.moebius.backend.domain.orders.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.moebius.backend.utils.ThreadScheduler.COMPUTE;
import static com.moebius.backend.utils.ThreadScheduler.IO;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderCacheService {
	private final OrderRepository orderRepository;

	@CacheEvict(value = "readyOrderCount", key = "{#exchange, #symbol, 'READY'}")
	public void evictReadyOrderCount(Exchange exchange, String symbol) {
		log.info("[Order] [{}/{}/{}] Evict cache.", exchange, symbol, OrderStatus.READY);
	}

	@Cacheable(value = "readyOrderCount", key = "{#exchange, #symbol, 'READY'}")
	public Mono<Long> getReadyOrderCountByExchangeAndSymbol(Exchange exchange, String symbol) {
		log.info("[Order] [{}/{}] Start to get count of orders from repository for warming cache up.", exchange, symbol);
		return orderRepository.countBySymbolAndExchangeAndOrderStatus(symbol, exchange, OrderStatus.READY)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.cache();
	}
}
