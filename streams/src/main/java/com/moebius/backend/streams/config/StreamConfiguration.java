package com.moebius.backend.streams.config;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.moebius.backend.streams.dto.TradeAggsDto;
import com.moebius.backend.streams.dto.TradeDto;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
public class StreamConfiguration {
    @Bean
    public Serdes.WrapperSerde<TradeDto> tradeDtoWrapperSerde(Gson gson) {
        return generateWrapperSerde(gson, TradeDto.class);
    }

    @Bean
    public Serdes.WrapperSerde<TradeAggsDto> tradeAggsWrapperSerde(Gson gson) {
        return generateWrapperSerde(gson, TradeAggsDto.class);
    }

    @Bean
    public Serdes.WrapperSerde<List<TradeAggsDto>> tradeAggsListWrapperSerde(Gson gson) {
        return generateWrapperSerdeForList(gson, new TypeToken<List<TradeAggsDto>>() {
        });
    }

    @Bean
    public Serdes.WrapperSerde<List<TradeDto>> tradeDtoListWrapperSerde(Gson gson) {
        return generateWrapperSerdeForList(gson, new TypeToken<List<TradeDto>>() {
        });
    }

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

    private static <T> Serdes.WrapperSerde<T> generateWrapperSerdeForList(Gson gson, TypeToken<T> typeToken) {
        return new Serdes.WrapperSerde<>((topic, data) -> gson.toJson(data).getBytes(StandardCharsets.UTF_8),
                ((topic, data) -> gson.fromJson(new String(data), typeToken.getType()))
        );
    }
}
