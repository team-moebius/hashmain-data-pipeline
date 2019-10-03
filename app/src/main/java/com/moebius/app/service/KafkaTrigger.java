package com.moebius.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaTrigger implements ApplicationListener {
//	private final List<KafkaConsumer> kafkaConsumers;

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
//		kafkaConsumers.forEach(KafkaConsumer::consumeMessages);
	}
}
