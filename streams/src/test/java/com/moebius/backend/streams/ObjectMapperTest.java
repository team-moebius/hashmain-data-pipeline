package com.moebius.backend.streams;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ObjectMapperTest {

    @Test
    public void tradeAggsDtoDeserializeTest(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))))
        .create();

        LocalDateTime now = LocalDateTime.of(2020,4,18,12,0);

        assert !"2020-04-18T12:00".equals(gson.toJson(now));
    }
}
