package com.moebius.backend.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(ElasticsearchProperties.class)
public class ElasticsearchConfiguration {
	private final ElasticsearchProperties elasticsearchProperties;

	@Bean
	public RestClientBuilder restClientBuilder() {
		String userName = elasticsearchProperties.getProperties().get("username");
		String password = elasticsearchProperties.getProperties().get("password");
		RestClientBuilder builder = RestClient.builder(HttpHost.create(elasticsearchProperties.getClusterNodes()));
		log.info("elasticsearch configuration - username:{} / cluster-nodes:{}", userName, elasticsearchProperties.getClusterNodes());

		if (userName != null && password != null) {
			BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));
			builder.setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
		}

		return builder;
	}

	@Bean
	public RestHighLevelClient restHighLevelClient(RestClientBuilder restClientBuilder) {
		return new RestHighLevelClient(restClientBuilder);
	}
}
