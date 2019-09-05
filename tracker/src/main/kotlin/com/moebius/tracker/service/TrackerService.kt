package com.moebius.tracker.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.moebius.backend.domain.commons.Exchange
import com.moebius.backend.domain.markets.MarketRepository
import com.moebius.backend.domain.trades.TradeDocumentRepository
import com.moebius.backend.utils.ThreadScheduler.COMPUTE
import com.moebius.backend.utils.ThreadScheduler.IO
import com.moebius.tracker.assembler.TradeAssembler
import com.moebius.tracker.dto.ExchangeRequestDto
import com.moebius.tracker.dto.TradeDto
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Service
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import org.springframework.web.reactive.socket.client.WebSocketClient
import reactor.core.publisher.Mono
import java.io.IOException
import java.net.URI

@Slf4j
@Service
@RequiredArgsConstructor
class TrackerService : ApplicationListener<ApplicationReadyEvent> {

    private val log = KotlinLogging.logger {}
    private val objectMapper = ObjectMapper().registerModule(KotlinModule())
            .registerModule(Jdk8Module())
            .registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    @Autowired
    private lateinit var webSocketClient: WebSocketClient

    @Autowired
    private lateinit var marketRepository: MarketRepository

    @Autowired
    private lateinit var openedSessions: MutableMap<String, WebSocketSession>

    @Autowired
    private lateinit var tradeDocumentRepository: TradeDocumentRepository

    @Autowired
    private lateinit var tradeAssembler: TradeAssembler

    @Value("\${exchange.upbit.websocket.uri}")
    private lateinit var uri: String

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        trackTrades()
    }

    private fun trackTrades() {
        marketRepository.findAllByExchange(Exchange.UPBIT)
                .subscribeOn(IO.scheduler())
                .publishOn(COMPUTE.scheduler())
                .map { it.symbol }
                .collectList()
                .map {
                    objectMapper.writeValueAsString(ExchangeRequestDto(it))
                }.map { message ->
                    log.info("[Tracker] Start to track trades. - message : {}", message)
                    webSocketClient.execute(URI.create(uri)) { session ->
                        log.info("[Tracker] Save opened session. [id : {}]", session.id)
                        openedSessions[session.id] = session
                        session.send(Mono.just(session.textMessage(message)))
                                .thenMany<WebSocketMessage>(session.receive().map<WebSocketMessage> { webSocketMessage ->
                                    try {
                                        val tradeDto = objectMapper.readValue(webSocketMessage.getPayloadAsText(), TradeDto::class.java)
                                        log.info { tradeDto }
                                        accumulateTrade(tradeDto)
                                    } catch (e: IOException) {
                                        log.error(e.message)
                                    }

                                    webSocketMessage
                                }).then()
                    }.subscribe()
                }.doOnTerminate(Runnable {
                    log.info("[Tracker] Session exited, Try to track trades again.")
                    openedSessions.clear()
                    trackTrades()
                }).subscribe()
    }

    private fun accumulateTrade(tradeDto: TradeDto) {
        Mono.fromCallable { tradeAssembler.toTradeDocument(tradeDto) }
                .subscribeOn(COMPUTE.scheduler())
                .publishOn(IO.scheduler())
                .flatMap { tradeDocumentRepository.saveAsync(it) }
                .subscribe()
    }
}