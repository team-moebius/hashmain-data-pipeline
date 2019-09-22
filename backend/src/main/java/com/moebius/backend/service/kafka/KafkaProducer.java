package com.moebius.backend.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

import java.util.Map;

import static com.moebius.backend.utils.ThreadScheduler.KAFKA;

/**
 * Base kafka message producer for concrete producer in moebius.
 *
 * @param <K> Message key
 * @param <V> Message value
 * @param <T> Correlation meta data type about sender record {@link SenderRecord} in reactor kafka
 */
@Slf4j
public abstract class KafkaProducer<K, V, T> {
	private final KafkaSender<K, V> sender;

	public KafkaProducer(Map<String, Object> senderDefaultProperties) {
		senderDefaultProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, getKeySerializerClass());
		senderDefaultProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, getValueSerializerClass());

		SenderOptions<K, V> senderOptions = SenderOptions.create(senderDefaultProperties);
		senderOptions.scheduler(KAFKA.scheduler());

		sender = KafkaSender.create(senderOptions);
	}

	public abstract String getTopic();

	protected abstract Class<?> getKeySerializerClass();

	protected abstract Class<?> getValueSerializerClass();

	protected K getKey(V message) {
		return null;
	}

	protected T getCorrelationMetadata(V message) {
		return null;
	}

	public Flux<SenderResult<T>> produceMessages(V message) {
		log.info("[Kafka] Start to send message. [{}]", message);
		return sender.send(Mono.just(SenderRecord.create(new ProducerRecord<>(getTopic(), getKey(message), message), getCorrelationMetadata(message))));
	}
}
