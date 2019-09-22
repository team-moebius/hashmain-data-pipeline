package com.moebius.backend.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConfiguration {
	private final KafkaProperties kafkaProperties;

	@Bean
	public Map<String, Object> senderDefaultProperties() {
		Map<String, Object> defaultProperties = new HashMap<>();

		defaultProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
		defaultProperties.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProperties.getProducer().getClientId());
		defaultProperties.put(ProducerConfig.ACKS_CONFIG, kafkaProperties.getProducer().getAcks());

		return defaultProperties;
	}

	@Bean
	public Map<String, Object> receiverDefaultProperties() {
		Map<String, Object> defaultProperties = new HashMap<>();

		defaultProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
		defaultProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, kafkaProperties.getConsumer().getClientId());
		defaultProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProperties.getConsumer().getAutoOffsetReset());

		return defaultProperties;
	}
}
