package com.moebius.tracker.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.moebius.backend.domain.trades.Symbol
import com.moebius.backend.service.TradeDocumentService
import com.moebius.tracker.assembler.TradeAssembler
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
import reactor.core.scheduler.Schedulers
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
    private lateinit var openedSessions: MutableMap<String, WebSocketSession>

    @Autowired
    private lateinit var tradeDocumentService: TradeDocumentService

    @Autowired
    private lateinit var tradeAssembler: TradeAssembler

    @Value("\${exchange.upbit.websocket.uri}")
    private lateinit var uri: String

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        trackTrades()
    }

    object RequestFormat {
        object Ticket {
            val ticket: String = "moebius-tracker"
        }

        object TypeCodes {
            val type: String = "trade"
            val codes: Array<Symbol> = Symbol.values()
        }

        object Format {
            val format: String = "SIMPLE"
        }

        fun toJsonString(mapper: ObjectMapper): String = mapper.writeValueAsString(listOf(Ticket, TypeCodes, Format))

    }

    private fun trackTrades() {
        val message = RequestFormat.toJsonString(objectMapper)
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
                            // maybe need to use upsertTrade rather accumulateTrade.
                            // upsertTrade(tradeDto);
                        } catch (e: IOException) {
                            log.error(e.message)
                        }

                        webSocketMessage
                    }).then()
        }.subscribe()
    }

    private fun accumulateTrade(tradeDto: TradeDto) {
        Mono.fromCallable { tradeAssembler.toTradeDocument(tradeDto) }
                .subscribeOn(Schedulers.parallel())
                .publishOn(Schedulers.elastic())
                .flatMap { tradeDocumentService.saveAsync(it) }
                .subscribe()
    }
}