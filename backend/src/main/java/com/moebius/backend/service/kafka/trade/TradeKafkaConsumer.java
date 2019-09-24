package com.moebius.backend.service.kafka.trade;

import com.moebius.backend.dto.TradeDto;
import com.moebius.backend.service.kafka.KafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.kafka.receiver.ReceiverOffset;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Map;

@Slf4j
@Component
public class TradeKafkaConsumer extends KafkaConsumer<String, TradeDto> {
	private static final String TRADE_KAFKA_TOPIC = "moebius.trade.upbit";

	public TradeKafkaConsumer(Map<String, Object> receiverDefaultProperties) {
		super(receiverDefaultProperties);
	}

	@Override
	public String getTopic() {
		return TRADE_KAFKA_TOPIC;
	}

	@Override
	public void processRecord(ReceiverRecord<String, TradeDto> record) {
		ReceiverOffset offset = record.receiverOffset();
		log.info("Received message: topic-partition={} offset={} timestamp={} key={} value={}\n",
			offset.topicPartition(),
			offset.offset(),
			record.timestamp(),
			record.key(),
			record.value());
		offset.acknowledge();
	}

	@Override
	protected Class<?> getKeyDeserializerClass() {
		return StringDeserializer.class;
	}

	@Override
	protected Class<?> getValueDeserializerClass() {
		return JsonDeserializer.class;
	}

	@Override
	public Disposable consumeMessages(TradeDto message) {
		return super.consumeMessages(message);
	}
}
