package com.moebius.backend.service.order.factory

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import static com.moebius.backend.domain.orders.OrderPosition.*

class OrderFactoryManagerTest extends Specification {
	@Shared
	def purchaseOrderFactory = new PurchaseOrderFactory()
	@Shared
	def saleOrderFactory = new SaleOrderFactory()
	@Shared
	def stoplossOrderFactory = new StoplossOrderFactory()

	def "Should get order factories"() {
		given:
		@Subject
		def orderFactoryManager = new OrderFactoryManager([purchaseOrderFactory, saleOrderFactory, stoplossOrderFactory])

		expect:
		ORDER_FACTORY == orderFactoryManager.getOrdersFactory(ORDER_POSITION)

		where:
		ORDER_POSITION || ORDER_FACTORY
		PURCHASE       || purchaseOrderFactory
		SALE           || saleOrderFactory
		STOPLOSS       || stoplossOrderFactory
	}
}
