package com.moebius.api.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.moebius.api.consumer.message.TradeStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Profile("!test")
@RequiredArgsConstructor
public class AggregatorConsumer implements ConsumerSeekAware {

    private final Cache<String, TradeStats> tradeStatsCache;
    private final ObjectMapper mapper;
    private final Map<TopicPartition, Long> topicPartitionOffsetMap;
    private CountDownLatch countDownLatch;

    @KafkaListener(topics = "${spring.kafka.consumer.topics}", groupId = "${spring.kafka.consumer.group-id}")
    public void message(ConsumerRecord<String, String> data) throws JsonProcessingException {

        var message = mapper.readValue(data.value(), TradeStats.class);
        putMessageCache(message);

        var topicPartition = new TopicPartition(data.topic(), data.partition());
        topicPartitionOffsetMap.computeIfPresent(topicPartition, (key, value) -> {
            if (data.offset() >= value) {
                countDownLatch.countDown();
                return null;
            }
            return value;
        });
    }

    private void putMessageCache(TradeStats tradeStats) {
        tradeStatsCache.put(tradeStats.getExchange() + "-" + tradeStats.getId(), tradeStats);
    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        this.topicPartitionOffsetMap.putAll(assignments);
        long timestamp = getConsumerStartTimestamp();
        callback.seekToTimestamp(assignments.keySet(), timestamp);
        countDownLatch = new CountDownLatch(assignments.size());
        log.info("set timestamp:{}", timestamp);
    }

    private long getConsumerStartTimestamp() {
        return getUnixTickNow() - TimeUnit.HOURS.toMillis(1);
    }

    private long getUnixTickNow() {
        return Instant.now().getEpochSecond() * 1000;
    }

    public boolean isReady() {
        return Optional.ofNullable(this.countDownLatch).map(o -> o.getCount() == 0L).orElse(false);
    }
}
