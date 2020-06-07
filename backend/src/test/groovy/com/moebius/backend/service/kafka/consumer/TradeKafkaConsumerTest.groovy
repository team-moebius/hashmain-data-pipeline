package com.moebius.backend.service.kafka.consumer

import reactor.core.publisher.Flux
import reactor.kafka.receiver.KafkaReceiver
import reactor.kafka.receiver.ReceiverRecord
import spock.lang.Specification
import spock.lang.Subject

import java.util.function.Consumer

import static com.moebius.backend.utils.ThreadScheduler.COMPUTE

class TradeKafkaConsumerTest extends Specification {
	def receiver = Mock(KafkaReceiver)
	def fluxWithReceiverRecord = Flux.just(Stub(ReceiverRecord))
	def consumer = Stub(Consumer)

	@Subject
	def tradeKafkaConsumer = new TradeKafkaConsumer([:])

	def "Should consume messages"() {
		given:
		1 * receiver.receive() >> fluxWithReceiverRecord
		1 * fluxWithReceiverRecord.publishOn(COMPUTE.scheduler())
		1 * fluxWithReceiverRecord.subscribe()

		when:
		tradeKafkaConsumer.consumeMessages()

		then:
		1 * receiver.receive() >> Flux.just(receiverRecord)
		1 * receiverRecord
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
