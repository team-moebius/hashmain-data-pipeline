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
	public Map<String, String> senderDefaultProperties() {
		Map<String, String> senderDefaultProperties = new HashMap<>();

		senderDefaultProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, String.join(",", kafkaProperties.getBootstrapServers()));
		senderDefaultProperties.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProperties.getProducer().getClientId());
		senderDefaultProperties.put(ProducerConfig.ACKS_CONFIG, kafkaProperties.getProducer().getAcks());

		return senderDefaultProperties;
	}

	@Bean
	public Map<String, String> receiverDefaultProperties() {
		Map<String, String> receiverDefaultProperties = new HashMap<>();

		receiverDefaultProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, String.join(",", kafkaProperties.getBootstrapServers()));
		receiverDefaultProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, kafkaProperties.getConsumer().getClientId());
		receiverDefaultProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProperties.getConsumer().getAutoOffsetReset());

		return receiverDefaultProperties;
	}
}
