package com.moebius.tracker.dto

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.moebius.backend.domain.commons.Change
import com.moebius.backend.domain.commons.Exchange
import com.moebius.backend.domain.commons.Symbol
import com.moebius.backend.domain.commons.TradeType
import com.moebius.backend.domain.trades.TradeDocument
import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.time.Instant
import java.time.ZoneId

class SerializeTest {

    private lateinit var mapper: ObjectMapper
    private val log = KotlinLogging.logger {}

    @Before
    fun init() {
        mapper = ObjectMapper().registerModule(KotlinModule())
                .registerModule(Jdk8Module())
                .registerModule(JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }

    @Test
    fun serialize_exchangeRequestDto_test() {
        val symbols = Symbol.values().toList()
        val test = mapper.writeValueAsString(listOf(ExchangeRequestDto.Ticket, ExchangeRequestDto.TypeCodes(codes = symbols), ExchangeRequestDto.Format))
        log.info { test }
        val subject = ExchangeRequestDto(symbols = symbols)
        val serializeResult = mapper.writeValueAsString(subject).apply {
            log.info { this }
        }
        assertThat(test).isEqualTo(serializeResult)
    }

    @Test
    fun test() {
        val subject = TradeDocument.of(Exchange.UPBIT,
                Symbol.KRW_BTC,
                TradeType.ASK,
                Change.RISE,
                2.toDouble(),
                3.toDouble(),
                4.toDouble(),
                5.toDouble())

        val serialized = mapper.writeValueAsString(subject)
        println(Instant.now().atZone(ZoneId.of("Asia/Seoul")))
        println(serialized)
    }
}