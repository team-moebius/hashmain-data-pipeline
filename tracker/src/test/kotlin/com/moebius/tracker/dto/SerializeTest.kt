package com.moebius.tracker.dto

import com.fasterxml.jackson.databind.ObjectMapper
import com.moebius.backend.domain.commons.Symbol
import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class SerializeTest {

    private lateinit var mapper: ObjectMapper
    private val log = KotlinLogging.logger {}

    @Before
    fun init() {
        mapper = ObjectMapper()
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
}