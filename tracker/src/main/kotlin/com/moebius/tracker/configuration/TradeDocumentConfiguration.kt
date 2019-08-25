package com.moebius.tracker.configuration

import com.moebius.backend.service.TradeDocumentService
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@RequiredArgsConstructor
class TradeDocumentConfiguration {

    @Value("\${spring.data.elasticsearch.cluster-nodes}")
    private lateinit var clusterNodes: String

    @Bean
    fun tradeDocumentService(): TradeDocumentService {
        return TradeDocumentService(clusterNodes)
    }

}