package com.moebius.api.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.moebius.api.consumer.message.TradeStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class AggregatorConsumer implements ConsumerSeekAware {

    @Value("${consumer.offset.rewind.ms:30000}")
    private long rewindMs;

    private final Cache<String, TradeStats> tradeStatsCache;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "${spring.kafka.consumer.topics}", groupId = "${spring.kafka.consumer.group-id}")
    public void message(ConsumerRecord<String, String> data) throws JsonProcessingException {
        var message = mapper.readValue(data.value(), TradeStats.class);
        putMessageCache(message);
    }

    private void putMessageCache(TradeStats tradeStats) {
        tradeStatsCache.put(tradeStats.getExchange() + "-" + tradeStats.getId(), tradeStats);
    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        long timestamp = getConsumerStartTimestamp();
        callback.seekToTimestamp(assignments.keySet(), timestamp);
        log.info("set timestamp:{}", timestamp);
    }

    private long getConsumerStartTimestamp() {
        return getUnixTickNow() - TimeUnit.HOURS.toMillis(1);
    }

    private long getUnixTickNow() {
        return Instant.now().getEpochSecond() * 1000;
    }
}
