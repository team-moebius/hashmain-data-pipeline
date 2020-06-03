package com.moebius.backend.service.exchange

import com.moebius.backend.assembler.exchange.UpbitAssembler
import com.moebius.backend.domain.apikeys.ApiKey
import com.moebius.backend.domain.commons.Exchange
import com.moebius.backend.domain.orders.Order
import com.moebius.backend.dto.exchange.upbit.UpbitAssetDto
import com.moebius.backend.dto.exchange.upbit.UpbitOrderDto
import com.moebius.backend.exception.WrongDataException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification
import spock.lang.Subject

import java.util.function.Consumer

class UpbitServiceTest extends Specification {
	def webClient = Mock(WebClient)
	def upbitAssembler = Mock(UpbitAssembler)
	def uriSpec = Mock(WebClient.RequestHeadersUriSpec)
	def headersSpec = Mock(WebClient.RequestHeadersSpec)
	def bodyUriSpec = Mock(WebClient.RequestBodyUriSpec)
	def bodySpec = Mock(WebClient.RequestBodySpec)
	def responseSpec = Mock(WebClient.ResponseSpec)

	def accessKey = "dummyAccessKey"
	def secretKey = "dummySecretKey"
	def authToken = "dummyAuthToken"
	def apiKey = Stub(ApiKey) {
		getAccessKey() >> accessKey
		getSecretKey() >> secretKey
	}
	def order = Stub(Order)


	@Subject
	def upbitService = new UpbitService(webClient, upbitAssembler)

	def setup() {
		upbitService.messageDigestHashAlgorithm = "SHA-512"
		upbitService.messageDigestCharset = "UTF-8"
	}

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

	def "Should check health when return ok response"() {
		given:
		1 * webClient.get() >> uriSpec
		1 * uriSpec.uri(_ as String) >> headersSpec
		1 * headersSpec.headers(_ as Consumer<HttpHeaders>) >> headersSpec
		1 * headersSpec.exchange() >> Mono.just(ClientResponse.create(HttpStatus.OK).build())

		expect:
		StepVerifier.create(upbitService.checkHealth(authToken))
				.assertNext({
					it != null
					it.statusCode() == HttpStatus.OK
				}).verifyComplete()
	}

	def "Should not check health when not return ok response"() {
		given:
		1 * webClient.get() >> uriSpec
		1 * uriSpec.uri(_ as String) >> headersSpec
		1 * headersSpec.headers(_ as Consumer<HttpHeaders>) >> headersSpec
		1 * headersSpec.exchange() >> Mono.just(ClientResponse.create(HttpStatus.UNAUTHORIZED).build())

		expect:
		StepVerifier.create(upbitService.checkHealth(authToken))
				.verifyError(WrongDataException.class)
	}

	def "Should request order and leave success log"() {
		given:
		1 * upbitAssembler.assembleOrderParameters(_ as Order) >> "dummyQueryParameter"
		1 * webClient.post() >> bodyUriSpec
		1 * bodyUriSpec.uri(_ as String) >> bodySpec
		1 * bodySpec.contentType(_ as MediaType) >> bodySpec
		1 * bodySpec.headers(_ as Consumer<HttpHeaders>) >> headersSpec
		1 * bodySpec.bodyValue(_ as UpbitOrderDto) >> headersSpec
		1 * headersSpec.exchange() >> Mono.just(ClientResponse.create(HttpStatus.CREATED).build())

		expect:
		StepVerifier.create(upbitService.requestOrder(apiKey, order))
				.assertNext({
					it != null
					it.statusCode() == HttpStatus.CREATED
				})
	}

	def "Should request order and leave fail log"() {
	}

	def "Should cancel order and leave success log"() {
	}

	def "Should cancel order and leave fail log"() {
	}

	def "Should get current order status"() {
	}

	def "Should not get current order status cause of unauthorized request"() {
	}
}
