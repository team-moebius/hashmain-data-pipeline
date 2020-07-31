package com.moebius.data.type;

import java.util.Arrays;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public enum Exchange {

    UPBIT("UPBIT");

    public String getName() {
        return name;
    }

    private String name;

    Exchange(String name) {
        this.name = name;
    }

    private static final Map<String, Exchange> map = Arrays.stream(Exchange.values()).collect(toMap(k -> k.getName(), k -> k));

    public static Exchange from(String name) {
        return map.getOrDefault(name.toUpperCase(), null);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
