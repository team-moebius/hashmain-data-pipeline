package com.moebius.batch;

import com.moebius.batch.tracker.configuration.TrackerConfiguration;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Properties;

@SpringBootApplication
public class MoebiusBatchApplication {

	public static void main(String[] args) {
		SpringApplication moebiusBatch = new SpringApplication(MoebiusBatchApplication.class);

		Properties properties = new Properties();
		properties.put("spring.batch.job.enabled", "false");
		moebiusBatch.setDefaultProperties(properties);

		moebiusBatch.run(args);
	}

	@Bean
	public ApplicationContextFactory trackerJobConfig() {
		return new GenericApplicationContextFactory(TrackerConfiguration.class);
	}

	@Bean
	public ReactorResourceFactory reactorResourceFactory() {
		return new ReactorResourceFactory();
	}

	@Bean
	public WebClient webClient() {
		ClientHttpConnector connector = new ReactorClientHttpConnector();

		return WebClient.builder().clientConnector(connector).build();
	}

}
