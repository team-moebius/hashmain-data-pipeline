package com.moebius.tracker.actuator

import com.moebius.backend.service.kafka.producer.TradeKafkaProducer
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.ReactiveHealthIndicator
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component("tracker")
class TrackerHealthCheckIndicator(
        private val tradeKafkaProducer: TradeKafkaProducer,
        meterRegistry: MeterRegistry
) : ReactiveHealthIndicator {

    private val uptime = meterRegistry.get("process.uptime").gauge();

    override fun health(): Mono<Health> {
        if (uptime.value() < 60) {
            return Mono.just(Health.up().build())
        }
        return tradeKafkaProducer.metricsFromProducer()
                .filter { o -> o.t1.name() == "request-rate" }
                .map { o -> o.t2.metricValue() as Double }
                .next()
                .map { o ->
                    if (o == 0.0) Health.down().build()
                    else Health.up()
                            .withDetail("uptime", uptime.value())
                            .withDetail("request-rate", o).build()
                }
    }

}