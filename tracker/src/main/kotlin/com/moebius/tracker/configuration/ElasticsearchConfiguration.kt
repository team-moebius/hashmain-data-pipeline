package com.moebius.tracker.configuration

import com.moebius.backend.domain.commons.Exchange
import com.moebius.tracker.service.MarketService
import mu.KotlinLogging
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestClientBuilder
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(ElasticsearchProperties::class)
class ElasticsearchConfiguration(private val elasticsearchProperties: ElasticsearchProperties) {
    private val log = KotlinLogging.logger {}

    @Autowired
    private lateinit var marketService: MarketService

    private fun clusterNodesHost(): List<HttpHost> {
        return elasticsearchProperties.clusterNodes.split(',').map { HttpHost.create(it) }
    }

    @Bean
    fun restClientBuilder(): RestClientBuilder {
        val userName = elasticsearchProperties.properties["username"]
        val password = elasticsearchProperties.properties["password"]
        val builder = RestClient.builder(
                *clusterNodesHost().toTypedArray()
                )
        log.info("elasticsearch configuration - username:$userName / cluster-nodes:${elasticsearchProperties.clusterNodes}")

        if (userName != null && password != null) {
            val credentialsProvider = BasicCredentialsProvider()
            credentialsProvider.setCredentials(AuthScope.ANY, UsernamePasswordCredentials(userName, password))
            builder.setHttpClientConfigCallback { it.setDefaultCredentialsProvider(credentialsProvider) }
        }

        return builder
    }

    @Bean
    fun restHighLevelClient(restClientBuilder: RestClientBuilder): RestHighLevelClient {
        return RestHighLevelClient(restClientBuilder)
    }

    @Bean
    fun marketCount(): Int {
        return marketService.getSymbolsByExchange(Exchange.UPBIT).block()!!.size
    }
}