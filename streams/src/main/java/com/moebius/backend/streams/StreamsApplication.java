package com.moebius.backend.streams;

import com.moebius.backend.streams.config.stream.RealtimeAggregationDebounceTopologyConfig;
import com.moebius.backend.streams.config.stream.RealtimeAggregationTopologyConfig;
import com.moebius.backend.streams.config.stream.ShortTermAggregationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@EnableKafkaStreams
@EnableConfigurationProperties(
        value = {
                RealtimeAggregationTopologyConfig.class,
                RealtimeAggregationDebounceTopologyConfig.class,
                ShortTermAggregationConfig.class
        }
)
@SpringBootApplication
public class StreamsApplication {
    public static void main(String[] args) {
        SpringApplication.run(StreamsApplication.class, args);
    }
}
