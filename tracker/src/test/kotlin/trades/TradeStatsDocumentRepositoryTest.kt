package trades

import com.moebius.backend.domain.commons.Exchange
import com.moebius.tracker.domain.trades.TradeStatsDocument
import com.moebius.tracker.domain.trades.TradeStatsDocumentRepository
import com.moebius.tracker.domain.trades.TradeStatsDocumentRepositoryImpl
import com.moebius.tracker.utils.ElasticUtils
import org.apache.http.HttpHost
import org.assertj.core.api.Assertions.assertThat
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@RunWith(SpringRunner::class)
class TradeStatsDocumentRepositoryTest {

    lateinit var subject: TradeStatsDocumentRepository

    @Before
    fun init() {
        subject = TradeStatsDocumentRepositoryImpl(
                RestHighLevelClient(RestClient.builder(HttpHost.create("http://localhost:9200")))
        )
    }

    @Test @Ignore
    fun test() {
        val target = TradeStatsDocument.of(
                Exchange.UPBIT,
                "KRW-BTC",
                ElasticUtils.AggregationInterval.EVERY_MINUTES,
                1,
                2.toDouble(),
                2,
                3.toDouble(),
                4,
                5.toDouble(),
                LocalDateTime.of(2019, 1, 1, 1, 1)
        )
        val id = subject.save(target)
        TimeUnit.SECONDS.sleep(1)
        val stats = subject.get(id)!!
        println("$stats")
        assertThat(target.id).isEqualTo(stats.id)
    }

    @Test @Ignore
    fun generateStats_test() {
        val result = subject.generateTradeStats(LocalDateTime.of(2019, 9, 7, 22, 25), ElasticUtils.AggregationInterval.EVERY_MINUTES)
        assertThat(result.size).isGreaterThan(0)
        subject.saveAll(result)
    }
}