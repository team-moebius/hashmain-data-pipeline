package com.moebius.backend.streams.topology;

import com.moebius.backend.streams.config.TradeEventTimeExtractor;
import com.moebius.backend.streams.config.stream.RealtimeAggregationTopologyConfig;
import com.moebius.backend.streams.dto.TradeAggsDto;
import com.moebius.backend.streams.dto.TradeDto;
import com.moebius.backend.streams.util.KafkaStreamsPropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class RealTimeAggregationTopology {

    private final RealtimeAggregationTopologyConfig config;

    public RealTimeAggregationTopology(RealtimeAggregationTopologyConfig config) {
        this.config = config;
    }

    @Bean
    public FactoryBean<StreamsBuilder> realTimeAggregationBuilder(KafkaStreamsConfiguration kafkaStreamsConfiguration) {
        final Map<String, Object> configs = KafkaStreamsPropertiesUtils.propertiesToMap(kafkaStreamsConfiguration.asProperties());
        configs.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, TradeEventTimeExtractor.class);
        configs.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, config.getFlushIntervalMs());
        configs.put(StreamsConfig.APPLICATION_ID_CONFIG, config.getApplicationId());
        return new StreamsBuilderFactoryBean(new KafkaStreamsConfiguration(configs));
    }

    @Bean
    public KStream<String, TradeDto> newTradeStream(StreamsBuilder realTimeAggregationBuilder, Serdes.WrapperSerde<TradeDto> serde) {
        return realTimeAggregationBuilder.stream(config.getSourceTopic(), Consumed.with(Serdes.String(), serde));
    }

    @Bean
    public Printed<Windowed<String>, TradeAggsDto> windowedTradeDtoPrinted() {
        return Printed.<Windowed<String>, TradeAggsDto>toSysOut().withKeyValueMapper(((key, value) -> String.format("[%s] %s %s/%s | %s %s",
                LocalDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_TIME),
                key.key(),
                key.window().startTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_TIME),
                key.window().endTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_TIME),
                value.getTotalTransactionCount(),
                value.getTotalTransactionVolume(),
                value.getStatsDate().format(DateTimeFormatter.ISO_LOCAL_TIME)
        )));
    }

    @Bean
    public Printed<Windowed<String>, List<TradeAggsDto>> windowedTradeDtoListPrinted() {
        return Printed.<Windowed<String>, List<TradeAggsDto>>toSysOut().withKeyValueMapper(((key, value) ->
                String.format("[%s] %s %s/%s | %s",
                        LocalDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_TIME),
                        key.key(),
                        key.window().startTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_TIME),
                        key.window().endTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_TIME),
                        value.size()
                )));
    }

    @Bean
    public KStream<String, TradeAggsDto> baseTradeAggStream(KStream<String, TradeDto> newTradeStream,
                                                            Serdes.WrapperSerde<TradeAggsDto> serde) {
        KStream<Windowed<String>, TradeAggsDto> stream = newTradeStream.groupByKey()
                .windowedBy(TimeWindows.of(Duration.ofSeconds(10)))
                .aggregate(TradeAggsDto::new, (k, v, acc) ->
                        acc.accumulate(v, "10s"), Materialized.with(Serdes.String(), serde))
                .toStream();
        KStream<String, TradeAggsDto> newStream = stream.selectKey((k, v) -> k.key());
        newStream.to("moebius.stream.develop.short-term.10s", Produced.with(Serdes.String(), serde));
        return newStream;
    }

    @Bean
    public KStream<Windowed<String>, List<TradeAggsDto>> shortTermAggStream(KStream<String, TradeAggsDto> baseTradeAggStream,
                                                                            Serdes.WrapperSerde<TradeAggsDto> serde,
                                                                            Serdes.WrapperSerde<List<TradeAggsDto>> serdeList
    ) {
        KStream<Windowed<String>, List<TradeAggsDto>> stream = baseTradeAggStream.groupByKey(Grouped.with(Serdes.String(), serde))
                .windowedBy(TimeWindows.of(Duration.ofMinutes(5)).advanceBy(Duration.ofMinutes(1)))
                .aggregate(ArrayList<TradeAggsDto>::new, (k, v, acc) -> {
                    acc.add(v);
                    return acc;
                }, Materialized.with(Serdes.String(), serdeList))
                .mapValues(RealTimeAggregationTopology::tradeAggsDtoMapper, Materialized.with(WindowedSerdes.timeWindowedSerdeFrom(String.class), serdeList))
                .toStream();
        stream.to("moebius.stream.develop.short-term.5m",
                Produced.with(WindowedSerdes.timeWindowedSerdeFrom(String.class), serdeList));
        return stream;
    }

//    @Bean
//    public KStream<Windowed<String>, List<TradeAggsDto>> shortTermAggStream(KStream<String, TradeAggsDto> baseTradeAggStream,
//                                                                            Serdes.WrapperSerde<List<TradeAggsDto>> serdeList
//    ) {
//        KStream<Windowed<String>, List<TradeAggsDto>> stream = baseTradeAggStream.groupByKey()
//                .windowedBy(TimeWindows.of(Duration.ofMinutes(5)))
//                .aggregate(ArrayList<TradeAggsDto>::new, (k, v, acc) -> {
//                    acc.add(v);
//                    return acc;
//                }, Materialized.with(Serdes.String(), serdeList))
//                .mapValues(RealTimeAggregationTopology::tradeAggsDtoMapper)
//                .toStream();
//        stream.to("moebius.stream.develop.short-term.5m",
//                Produced.with(WindowedSerdes.timeWindowedSerdeFrom(String.class), serdeList));
//        return stream;
//    }

    private static List<TradeAggsDto> tradeAggsDtoMapper(Windowed<String> key, List<TradeAggsDto> value) {
        Instant startTime = key.window().startTime();
        Map<Long, TradeAggsDto> map = new HashMap<>();
        for (TradeAggsDto trade : value) {
            long diff = trade.getStartDate().toEpochSecond() - startTime.getEpochSecond();
            long pos = diff / TimeUnit.MINUTES.toSeconds(1);
            TradeAggsDto aggsDto = map.getOrDefault(pos, new TradeAggsDto());
            map.put(pos, aggsDto.accumulate(trade, "1m"));
        }

        return new ArrayList<>(map.values());
    }
}
