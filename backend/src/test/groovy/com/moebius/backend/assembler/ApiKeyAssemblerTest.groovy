package com.moebius.backend.assembler

import com.moebius.backend.domain.apikeys.ApiKey
import com.moebius.backend.domain.commons.Exchange
import com.moebius.backend.dto.frontend.ApiKeyDto
import com.moebius.backend.dto.frontend.response.ApiKeyResponseDto
import org.bson.types.ObjectId
import spock.lang.Specification
import spock.lang.Subject

class ApiKeyAssemblerTest extends Specification {
	@Subject
	def apiKeyAssembler = new ApiKeyAssembler()

	def "Should assemble api key"() {
		given:
		def apiKeyDto = Stub(ApiKeyDto) {
			getExchange() >> Exchange.UPBIT
		}

		when:
		def result = apiKeyAssembler.assembleApiKey(apiKeyDto, "5d8620bf46e0fb0001d64265")

		then:
		result instanceof ApiKey
		result.getExchange() == Exchange.UPBIT
		result.getMemberId().toString() == "5d8620bf46e0fb0001d64265"
	}

	def "Should assemble api response"() {
		given:
		def apiKey = Stub(ApiKey) {
			getId() >> new ObjectId("5d8620bf46e0fb0001d64265")
			getExchange() >> Exchange.UPBIT
		}

		when:
		def result = apiKeyAssembler.assembleResponse(apiKey)

		then:
		result instanceof ApiKeyResponseDto
		result.getId() == "5d8620bf46e0fb0001d64265"
		result.getExchange() == Exchange.UPBIT
	}
}
