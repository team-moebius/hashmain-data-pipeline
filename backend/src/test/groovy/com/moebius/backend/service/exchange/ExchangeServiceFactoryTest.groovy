package com.moebius.backend.service.exchange

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import static com.moebius.backend.domain.commons.Exchange.UPBIT

class ExchangeServiceFactoryTest extends Specification {
	@Shared
	def upbitService = Stub(UpbitService)

	def "Should get #EXCHANGE service"() {
		given:
		def exchangeServices = [upbitService]

		@Subject
		def exchangeServiceFactory = new ExchangeServiceFactory(exchangeServices)

		expect:
		EXCHANGE_SERVICE == exchangeServiceFactory.getService(EXCHANGE)

		where:
		EXCHANGE || EXCHANGE_SERVICE
		UPBIT    || upbitService
	}
}
