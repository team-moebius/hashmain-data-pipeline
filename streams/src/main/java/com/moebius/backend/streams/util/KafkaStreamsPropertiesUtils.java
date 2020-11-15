package com.moebius.backend.streams.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class KafkaStreamsPropertiesUtils {
    public static Map<String, Object> propertiesToMap(Properties properties) {
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            map.put((String) entry.getKey(), entry.getValue());
        }
        return map;
    }
}
