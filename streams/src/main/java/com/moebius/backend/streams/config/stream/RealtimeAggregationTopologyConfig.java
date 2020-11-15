package com.moebius.backend.streams.config.stream;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "stream.real-time")
public class RealtimeAggregationTopologyConfig {
    @NotNull
    @NotEmpty
    private String sourceTopic;
    @NotNull
    @NotEmpty
    private String outputTopic;
    @Positive
    @NotNull
    private Long flushIntervalMs;
    @NotNull
    @NotEmpty
    private String applicationId;
    @Min(1)
    @NotNull
    private Long aggregationIntervalMinutes;
}
