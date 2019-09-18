package com.moebius.backend.domain.trades

import org.apache.http.HttpHost
import org.assertj.core.api.Assertions.assertThat
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import java.time.LocalDateTime

@Ignore
class TradeDocumentRepositoryTest {
    lateinit var subject: TradeDocumentRepository

    @Before
    fun init() {
        subject = TradeDocumentRepositoryImpl(
                RestHighLevelClient(RestClient.builder(HttpHost.create("http://localhost:9200")))
        )
    }

    @Test
    fun getByDateTimeRange_test() {
        val start = LocalDateTime.of(2019, 9, 7, 22, 25)
        val end = LocalDateTime.of(2019, 9, 7, 22, 26)
        val result = subject.getByDateTimeRange(start, end)
        assertThat(result.size).isGreaterThan(0)
    }
}