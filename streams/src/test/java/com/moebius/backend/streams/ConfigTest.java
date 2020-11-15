package com.moebius.backend.streams;

import com.moebius.backend.streams.config.stream.RealtimeAggregationTopologyConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {
        "stream.real-time.source-topic=asdf",
        "stream.real-time.output-topic=asdf",
        "stream.real-time.flush-interval-ms=1",
        "stream.real-time.application-id=asdf",
        "stream.real-time.aggregation-interval-minutes=1",
})
public class ConfigTest {
    @Autowired
    private RealtimeAggregationTopologyConfig config;

    @Test
    public void test() {
        assertNotNull(config);
    }


    @EnableConfigurationProperties(value = RealtimeAggregationTopologyConfig.class)
    @org.springframework.boot.test.context.TestConfiguration
    static class TestConfiguration {

    }
}
