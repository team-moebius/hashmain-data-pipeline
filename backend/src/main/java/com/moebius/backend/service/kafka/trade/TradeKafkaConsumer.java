package com.moebius.backend.service.kafka.trade;

import com.moebius.backend.domain.trades.TradeDocument;
import com.moebius.backend.service.kafka.KafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.kafka.receiver.ReceiverOffset;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Map;

@Slf4j
@Component
public class TradeKafkaConsumer extends KafkaConsumer<String, TradeDocument> {
	private static final String TRADE_KAFKA_TOPIC = "moebius.trade.upbit";
	
	public TradeKafkaConsumer(@Qualifier("receiverDefaultProperties") Map<String, Object> receiverDefaultProperties) {
		super(receiverDefaultProperties);
	}

	@Override
	public String getTopic() {
		return TRADE_KAFKA_TOPIC;
	}

	@Override
	public void processRecord(ReceiverRecord<String, TradeDocument> record) {
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
	public Disposable consumeMessages(TradeDocument message) {
		return super.consumeMessages(message);
	}
}
