package com.moebius.backend.service.exchange

import com.moebius.backend.assembler.exchange.UpbitAssembler
import com.moebius.backend.domain.commons.Exchange
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.StepVerifier
import spock.lang.Specification
import spock.lang.Subject

class UpbitServiceTest extends Specification {
	def webClient = Mock(WebClient)
	def upbitAssembler = Mock(UpbitAssembler)
	def accessKey = "accessKey"
	def secretKey = "secretKey"

	@Subject
	def upbitService = new UpbitService(webClient, upbitAssembler)

	def "Should get exchange type of upbit"() {
		expect:
		upbitService.getExchange() == Exchange.UPBIT
	}

	def "Should get auth token"() {
		expect:
		StepVerifier.create(upbitService.getAuthToken(accessKey, secretKey))
				.assertNext({
					it != null
					it.length() > 0
				}).verifyComplete()
	}

	def "Should get assets"() {
	}

	def "Should check health"() {
	}

	def "RequestOrder"() {
	}

	def "CancelOrder"() {
	}

	def "GetCurrentOrderStatus"() {
	}
}
