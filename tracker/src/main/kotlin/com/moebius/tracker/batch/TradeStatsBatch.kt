package com.moebius.tracker.batch

import com.moebius.backend.domain.trades.TradeStatsDocumentRepository
import com.moebius.backend.utils.ElasticUtils
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TradeStatsBatch(
        @Autowired val tradeStatsDocumentRepository: TradeStatsDocumentRepository
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