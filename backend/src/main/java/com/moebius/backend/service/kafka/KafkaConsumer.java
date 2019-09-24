package com.moebius.backend.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import reactor.core.Disposable;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Collections;
import java.util.Map;

import static com.moebius.backend.utils.ThreadScheduler.KAFKA;

@Slf4j
public abstract class KafkaConsumer<K, V> {
	private final KafkaReceiver<K, V> receiver;

	public KafkaConsumer(Map<String, Object> receiverDefaultProperties) {
		receiverDefaultProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, getKeyDeserializerClass());
		receiverDefaultProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, getValueDeserializerClass());

		ReceiverOptions<K, V> receiverOptions = ReceiverOptions.create(receiverDefaultProperties);
		receiverOptions.schedulerSupplier(KAFKA::scheduler);
		receiverOptions.subscription(Collections.singleton(getTopic()))
			.addAssignListener(partitions -> log.debug("[Kafka] onPartitionsAssigned {}", partitions))
			.addRevokeListener(partitions -> log.debug("[Kafka] onPartitionsRevoked {}", partitions));

		receiver = KafkaReceiver.create(receiverOptions);
	}

	public abstract String getTopic();

	public abstract void processRecord(ReceiverRecord<K, V> record);

	protected abstract Class<?> getKeyDeserializerClass();

	protected abstract Class<?> getValueDeserializerClass();

	public Disposable consumeMessages(V message) {
		log.info("[Kafka] Start read message. [{}]", message);
		return receiver.receive().subscribe(this::processRecord);
	}
}
