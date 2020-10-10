package com.moebius.backend.streams.topology;

import com.moebius.backend.streams.config.stream.RealtimeAggregationDebounceTopologyConfig;
import com.moebius.backend.streams.dto.TradeAggsDto;
import com.moebius.backend.streams.util.KafkaStreamsPropertiesUtils;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

import java.util.Map;

//@Configuration
public class RealtimeAggregationDebounceTopology {

    private final RealtimeAggregationDebounceTopologyConfig config;

    public RealtimeAggregationDebounceTopology(RealtimeAggregationDebounceTopologyConfig config) {
        this.config = config;
    }

    @Bean
    public FactoryBean<StreamsBuilder> debounceStreamsBuilder(KafkaStreamsConfiguration kafkaStreamsConfiguration) {
        final Map<String, Object> configs = KafkaStreamsPropertiesUtils.propertiesToMap(kafkaStreamsConfiguration.asProperties());
        configs.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, config.getFlushIntervalMs());
        configs.put(StreamsConfig.APPLICATION_ID_CONFIG, config.getApplicationId());
        return new StreamsBuilderFactoryBean(new KafkaStreamsConfiguration(configs));
    }

    @Bean
    public KStream<String, TradeAggsDto> windowedTradeAggsDtoKStream(StreamsBuilder debounceStreamsBuilder,
                                                                     Serdes.WrapperSerde<TradeAggsDto> serde) {
        return debounceStreamsBuilder.stream(config.getSourceTopic(), Consumed.with(Serdes.String(), serde));
    }

    @Bean
    public KStream<String, TradeAggsDto> reduceStream(KStream<String, TradeAggsDto> windowedTradeAggsDtoKStream) {
        KStream<String, TradeAggsDto> reduced = windowedTradeAggsDtoKStream.groupByKey()
                .reduce((v1, v2) -> {
                            if (v1 == null || v1.getEndDate() == null) return v2;
                            if (v2 == null || v2.getEndDate() == null) return v1;
                            return v1.getEndDate().isAfter(v2.getEndDate()) ? v1 : v2;
                        }
                )
                .toStream();

        reduced.to(config.getOutputTopic());
        return reduced;
    }

}
