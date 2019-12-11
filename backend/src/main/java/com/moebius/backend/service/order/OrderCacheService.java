package com.moebius.backend.service.order;

import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.orders.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderCacheService {
	@CacheEvict(value = "readyOrderCount", key = "{#exchange, #symbol, 'READY'}")
	public void evictOrderCount(Exchange exchange, String symbol) {
		log.info("[Order] [{}/{}/{}] Evict cache.", exchange, symbol, OrderStatus.READY);
	}
}
