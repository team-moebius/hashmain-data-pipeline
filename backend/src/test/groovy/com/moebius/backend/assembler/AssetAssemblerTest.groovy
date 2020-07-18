package com.moebius.backend.assembler

import com.moebius.backend.dto.exchange.AssetDto
import com.moebius.backend.dto.exchange.upbit.UpbitAssetDto
import com.moebius.backend.dto.frontend.response.AssetResponseDto
import spock.lang.Specification
import spock.lang.Subject

class AssetAssemblerTest extends Specification {
	def assets = [UpbitAssetDto.builder().currency("BTC").build(),
				  UpbitAssetDto.builder().currency("ETH").build(),
				  UpbitAssetDto.builder().currency("BCH").build(),
				  UpbitAssetDto.builder().currency("XRP").build(),
				  UpbitAssetDto.builder().currency("EOS").build()]
	@Subject
	def assetAssembler = new AssetAssembler()

	def "Should assemble response"() {
		when:
		def result = assetAssembler.assembleResponse(assets)

		then:
		result instanceof AssetResponseDto
		result.getAssets() instanceof List
		result.getAssets().size() == 5
		result.getAssets().get(0) instanceof AssetDto
	}

	def "Should assemble currency assets"() {
		when:
		def result = assetAssembler.assembleCurrencyAssets(assets)

		then:
		result instanceof Map
		result.size() == 5
		result.get("BTC") instanceof AssetDto
	}
}
