package com.moebius.tracker.batch

import com.moebius.tracker.domain.trades.TradeStatsDocumentRepository
import com.moebius.tracker.utils.ElasticUtils
import mu.KotlinLogging
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Profile("!local")
class TradeStatsBatch(
        private val tradeStatsDocumentRepository: TradeStatsDocumentRepository
) {
    private val log = KotlinLogging.logger {}

    @Scheduled(cron = "0 * * * * *")
    fun execute() {
        log.info("execute trade stream stats batch")
        val startDateTime = LocalDateTime.now().minusMinutes(2L).withNano(0)

        val statsList = tradeStatsDocumentRepository.generateTradeStats(startDateTime, ElasticUtils.AggregationInterval.EVERY_MINUTES)
        tradeStatsDocumentRepository.saveAll(statsList)
    }
}