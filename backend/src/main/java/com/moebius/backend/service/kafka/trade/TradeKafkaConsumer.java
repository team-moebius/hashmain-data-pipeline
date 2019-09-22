package com.moebius.backend.service.kafka.trade;

import com.moebius.backend.dto.TradeDto;
import com.moebius.backend.service.kafka.KafkaConsumer;
import org.springframework.stereotype.Component;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Map;
import java.util.function.Consumer;

@Component
public class TradeKafkaConsumer extends KafkaConsumer<String, TradeDto> {

	public TradeKafkaConsumer(Map<String, Object> receiverDefaultProperties) {
		super(receiverDefaultProperties);
	}

	@Override
	public String getTopic() {
		return null;
	}

	@Override
	public Consumer<? extends ReceiverRecord<String, TradeDto>> processRecord(ReceiverRecord<String, TradeDto> record) {
		return null;
	}

	@Override
	protected Class<?> getKeyDeserializerClass() {
		return null;
	}

	@Override
	protected Class<?> getValueDeserializerClass() {
		return null;
	}
}
