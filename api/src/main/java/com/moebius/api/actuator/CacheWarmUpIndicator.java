package com.moebius.api.actuator;

import com.moebius.api.consumer.AggregatorConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("cache-warmup")
@RequiredArgsConstructor
public class CacheWarmUpIndicator implements ReactiveHealthIndicator {

    private final AggregatorConsumer aggregatorConsumer;

    @Override
    public Mono<Health> getHealth(boolean includeDetails) {
        return health();
    }

    @Override
    public Mono<Health> health() {
        if(aggregatorConsumer.isReady())
            return Mono.just(Health.up().build());
        return Mono.just(Health.down().build());
    }
}
