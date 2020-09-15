package com.moebius.backend.streams;

import com.google.gson.*;
import com.moebius.backend.streams.dto.TradeAggsDto;
import com.moebius.backend.streams.dto.TradeDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
@Slf4j
public class StreamConfiguration {

    private final static long AGGS_TIME_INTERVAL = 60000L;
    public static final String TARGET_TOPIC = "moebius.trade.upbit";

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(ZonedDateTime.class, (JsonDeserializer<ZonedDateTime>) (src, typeOfSrc, context) -> ZonedDateTime.parse(src.getAsString(), DateTimeFormatter.ISO_DATE_TIME))
                .registerTypeAdapter(ZonedDateTime.class, (JsonSerializer<ZonedDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_DATE_TIME)))
                .create();
    }

    private static <T> Serdes.WrapperSerde<T> generateWrapperSerde(Gson gson, Class<T> typeClass) {
        return new Serdes.WrapperSerde<>((topic, data) -> gson.toJson(data).getBytes(StandardCharsets.UTF_8),
                ((topic, data) -> gson.fromJson(new String(data), typeClass))
        );
    }

    @Bean
    public Serdes.WrapperSerde<TradeDto> tradeDtoWrapperSerde(Gson gson) {
        return generateWrapperSerde(gson, TradeDto.class);
    }

    @Bean
    public Serdes.WrapperSerde<TradeAggsDto> tradeAggsWrapperSerde(Gson gson) {
        return generateWrapperSerde(gson, TradeAggsDto.class);
    }

    @Bean
    public KStream<String, TradeDto> tradeStream(StreamsBuilder streamsBuilder, Serdes.WrapperSerde<TradeDto> serde) {
        return streamsBuilder.stream(TARGET_TOPIC, Consumed.with(Serdes.String(), serde));
    }

    @Bean
    public KStream<String, TradeDto> newKeyStream(KStream<String, TradeDto> tradeStream) {
        return tradeStream.selectKey((k, v) -> v.getExchange() + v.getSymbol() + (v.getReceivedTime() / AGGS_TIME_INTERVAL));
    }

    @Bean
    public KStream<String, TradeAggsDto> tradeAggsDtoKStream(KStream<String, TradeDto> newKeyStream,
                                                             Serdes.WrapperSerde<TradeDto> tradeDtoWrapperSerde,
                                                             Serdes.WrapperSerde<TradeAggsDto> serde) {
        KStream<String, TradeAggsDto> stream =
                newKeyStream.groupByKey(Grouped.with(Serdes.String(), tradeDtoWrapperSerde))
                        .aggregate(TradeAggsDto::new, (k, v, acc) -> acc.accumulate(k, v), Materialized.with(Serdes.String(), serde))
                        .toStream();
        stream.print(Printed.toSysOut());
        stream.to("trade-stats", Produced.with(Serdes.String(), serde));
        return stream;
    }
}
