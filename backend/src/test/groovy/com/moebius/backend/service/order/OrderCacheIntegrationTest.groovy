package com.moebius.backend.service.order

import com.moebius.backend.domain.commons.Exchange
import com.moebius.backend.domain.orders.OrderRepository
import com.moebius.backend.domain.orders.OrderStatus
import com.moebius.backend.service.order.OrderCacheService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ContextConfiguration
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification
import spock.lang.Subject
import spock.mock.DetachedMockFactory

@ContextConfiguration
class OrderCacheIntegrationTest extends Specification {
	@TestConfiguration
	@EnableCaching
	static class Config {
		def mockFactory = new DetachedMockFactory()
		def orderRepository = mockFactory.Mock(OrderRepository)

		@Bean
		CacheManager cacheManager() {
			new ConcurrentMapCacheManager("readyOrderCount")
		}

		@Bean
		OrderRepository orderRepository() {
			orderRepository
		}

		@Bean
		OrderCacheService orderCacheService() {
			new OrderCacheService(orderRepository)
		}
	}

	@Autowired
	CacheManager cacheManager

	@Autowired
	OrderRepository orderRepository

	@Subject
	@Autowired
	OrderCacheService orderCacheService

	def "setup"() {
		cacheManager.getCache("readyOrderCount").clear()
	}

	def "get order from db, cache and evict then get order from db again"() {
		when:
		StepVerifier.create(orderCacheService.getReadyOrderCountByExchangeAndSymbol(Exchange.UPBIT, "KRW-BTC"))
				.expectNext(1L)
				.verifyComplete()
		StepVerifier.create(orderCacheService.getReadyOrderCountByExchangeAndSymbol(Exchange.UPBIT, "KRW-BTC"))
				.expectNext(1L)
				.verifyComplete()
		orderCacheService.evictReadyOrderCount(Exchange.UPBIT, "KRW-BTC")
		StepVerifier.create(orderCacheService.getReadyOrderCountByExchangeAndSymbol(Exchange.UPBIT, "KRW-BTC"))
				.expectNext(1L)
				.verifyComplete()

		then:
		2 * orderRepository.countBySymbolAndExchangeAndOrderStatus("KRW-BTC", Exchange.UPBIT, OrderStatus.READY) >> Mono.just(1L)
	}
}
