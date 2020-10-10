package com.moebius.backend.streams.config.stream;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "stream.debounce")
public class RealtimeAggregationDebounceTopologyConfig {
    @NotNull
    @NotEmpty
    String sourceTopic;
    @NotNull
    @NotEmpty
    String outputTopic;
    @Positive
    @NotNull
    Long flushIntervalMs;
    @NotNull
    @NotEmpty
    String applicationId;
}
