package com.moebius.backend.configuration


import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@EnableConfigurationProperties(ElasticsearchProperties::class)
class ElasticsearchConfiguration(
        var elasticsearchProperties: ElasticsearchProperties
) {

    @Bean
    fun restHighLevelClient(): RestHighLevelClient {
        return RestHighLevelClient(RestClient.builder(HttpHost.create(elasticsearchProperties.clusterNodes)))
    }

}