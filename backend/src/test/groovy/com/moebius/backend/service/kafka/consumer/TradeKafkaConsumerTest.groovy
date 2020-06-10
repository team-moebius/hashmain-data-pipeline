package com.moebius.backend.service.kafka.consumer

import com.moebius.backend.dto.TradeDto
import com.moebius.backend.service.market.MarketService
import com.moebius.backend.service.order.ExchangeOrderService
import reactor.kafka.receiver.ReceiverRecord
import spock.lang.Specification
import spock.lang.Subject

class TradeKafkaConsumerTest extends Specification {
	def receiverRecord = Stub(ReceiverRecord) {
		value() >> Stub(TradeDto)
	}

	@Subject
	def tradeKafkaConsumer = new TradeKafkaConsumer([:], Stub(ExchangeOrderService), Stub(MarketService))

	def "Should consume messages"() {
		when:
		tradeKafkaConsumer.consumeMessages()

		then:
		tradeKafkaConsumer.processRecord(receiverRecord)
	}

	def "Should get topic"() {

	}

	def "ProcessRecord"() {

	}

	def "GetKeyDeserializerClass"() {
	}

	def "GetValueDeserializerClass"() {
	}
}
