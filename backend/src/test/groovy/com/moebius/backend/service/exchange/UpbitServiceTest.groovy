package com.moebius.backend.service.exchange

import com.moebius.backend.assembler.exchange.UpbitAssembler
import org.springframework.web.reactive.function.client.WebClient
import spock.lang.Specification
import spock.lang.Subject

class UpbitServiceTest extends Specification {
	def webClient = Mock(WebClient)
	def upbitAssembler = Mock(UpbitAssembler)

	@Subject
	def upbitService = new UpbitService()

	def "GetExchange"() {

	}

	def "GetAuthToken"() {
	}

	def "GetAssets"() {
	}

	def "CheckHealth"() {
	}

	def "RequestOrder"() {
	}

	def "CancelOrder"() {
	}

	def "GetCurrentOrderStatus"() {
	}
}
