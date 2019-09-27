package com.moebius.tracker.configuration

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("com.moebius.backend.service.kafka")
class KafkaConfiguration {
}