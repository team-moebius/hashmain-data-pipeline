package com.moebius.backend.service.exchange

import com.moebius.backend.assembler.exchange.UpbitAssembler
import com.moebius.backend.domain.commons.Exchange
import com.moebius.backend.dto.exchange.upbit.UpbitAssetDto
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import spock.lang.Specification
import spock.lang.Subject

import java.util.function.Consumer

class UpbitServiceTest extends Specification {
	def webClient = Mock(WebClient)
	def upbitAssembler = Mock(UpbitAssembler)
	def uriSpec = Mock(WebClient.RequestHeadersUriSpec)
	def headersSpec = Mock(WebClient.RequestHeadersSpec)
	def responseSpec = Mock(WebClient.ResponseSpec)

	def accessKey = "accessKey"
	def secretKey = "secretKey"
	def authToken = "authToken"

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
		given:
		1 * webClient.get() >> uriSpec
		1 * uriSpec.uri(_ as String) >> headersSpec
		1 * headersSpec.headers(_ as Consumer<HttpHeaders>) >> headersSpec
		1 * headersSpec.retrieve() >> responseSpec
		1 * responseSpec.bodyToFlux(UpbitAssetDto.class) >> Flux.just(UpbitAssetDto.builder()
				.currency("BTC")
				.balance("10000")
				.averagePurchasePrice("2000000")
				.build())

		expect:
		StepVerifier.create(upbitService.getAssets(authToken))
				.assertNext({
					it != null
					it.getCurrency() == "BTC"
					it.getBalance() == 10000D
					it.getAveragePurchasePrice() == 2000000D
				}).verifyComplete()
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
