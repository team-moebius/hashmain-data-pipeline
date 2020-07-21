package com.moebius.backend.assembler.exchange

import com.fasterxml.jackson.databind.ObjectMapper
import com.moebius.backend.domain.orders.Order
import com.moebius.backend.dto.OrderStatusDto
import com.moebius.backend.dto.exchange.upbit.UpbitOrderDto
import com.moebius.backend.dto.exchange.upbit.UpbitOrderStatusDto
import org.bson.types.ObjectId
import spock.lang.Specification
import spock.lang.Subject

class UpbitAssemblerTest extends Specification {
	def orderId = "5eb6df8abd5d1a46cfcef11b"
	def order = Stub(Order) {
		getId() >> new ObjectId("5eb6df8abd5d1a46cfcef11b")
	}
	def objectMapper = Mock(ObjectMapper)

	@Subject
	def upbitAssembler = new UpbitAssembler(objectMapper)

	def "Should assemble order"() {
		when:
		def result = upbitAssembler.assembleOrder(order)

		then:
		result instanceof UpbitOrderDto
		result.getIdentifier() == orderId
	}

	def "Should assemble order status"() {
		when:
		def result = upbitAssembler.assembleOrderStatus(orderId, Stub(UpbitOrderStatusDto))

		then:
		result instanceof OrderStatusDto
		result.getId() == orderId
	}

	def "Should assemble order parameters"() {

	}

	def "Should parse order position"() {

	}

	def "Should parse order status"() {

	}

	def "Should parse order price"() {

	}

	def "Should parse order volume"() {

	}
}
