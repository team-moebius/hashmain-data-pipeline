package com.moebius.tracker.service

import com.moebius.backend.domain.commons.Exchange
import com.moebius.backend.utils.ThreadScheduler.COMPUTE
import com.moebius.backend.utils.ThreadScheduler.IO
import com.moebius.tracker.dto.upbit.UpbitMarketDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class MarketService(private val webClient: WebClient) {
    @Value("\${exchange.upbit.rest.public-uri}")
    private lateinit var publicUri: String
    @Value("\${exchange.upbit.rest.market}")
    private lateinit var marketUri: String

    fun getSymbolsByExchange(exchange: Exchange): Mono<List<String>> =
            webClient.get()
                    .uri(publicUri + marketUri)
                    .retrieve()
                    .bodyToFlux(UpbitMarketDto::class.java)
                    .subscribeOn(IO.scheduler())
                    .publishOn(COMPUTE.scheduler())
                    .filter { dto -> dto.market.startsWith("KRW") }
                    .map { dto -> dto.market }
                    .collectList()
}