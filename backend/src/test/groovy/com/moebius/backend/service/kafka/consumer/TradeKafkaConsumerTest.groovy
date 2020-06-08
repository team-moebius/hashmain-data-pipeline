package com.moebius.backend.service.kafka.consumer


import com.moebius.backend.service.market.MarketService
import com.moebius.backend.service.order.ExchangeOrderService
import reactor.core.publisher.Flux
import reactor.kafka.receiver.KafkaReceiver
import reactor.kafka.receiver.ReceiverRecord
import reactor.test.StepVerifier
import spock.lang.Specification
import spock.lang.Subject

import java.util.function.Consumer

import static com.moebius.backend.utils.ThreadScheduler.COMPUTE

class TradeKafkaConsumerTest extends Specification {
	def receiver = Mock(KafkaReceiver)
	def fluxWithReceiverRecord = Flux.just(Stub(ReceiverRecord))
	def consumer = Stub(Consumer)

	@Subject
	def tradeKafkaConsumer = new TradeKafkaConsumer([:], Stub(ExchangeOrderService), Stub(MarketService))

	def "Should consume messages"() {
		given:
		1 * receiver.receive() >> fluxWithReceiverRecord
		1 * fluxWithReceiverRecord.publishOn(COMPUTE.scheduler()) >> fluxWithReceiverRecord
		1 * fluxWithReceiverRecord.subscribe(consumer)

		expect:
		StepVerifier.create(tradeKafkaConsumer.consumeMessages())
				.expectSubscription()
	}

	def "GetTopic"() {

	}

	def "ProcessRecord"() {
	}

	def "GetKeyDeserializerClass"() {
	}

	def "GetValueDeserializerClass"() {
	}
}
