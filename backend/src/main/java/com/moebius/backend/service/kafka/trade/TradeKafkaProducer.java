package com.moebius.backend.service.kafka.trade;

import com.moebius.backend.dto.TradeDto;
import com.moebius.backend.service.kafka.KafkaProducer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TradeKafkaProducer extends KafkaProducer<String, TradeDto, String> {
	public TradeKafkaProducer(Map<String, Object> senderDefaultProperties) {
		super(senderDefaultProperties);
	}

	@Override
	public String getTopic() {
		return null;
	}

	@Override
	protected Class<?> getKeySerializerClass() {
		return null;
	}

	@Override
	protected Class<?> getValueSerializerClass() {
		return null;
	}
}
