package com.moebius.tracker.configuration

import com.moebius.backend.configuration.KafkaConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@ComponentScan("com.moebius.backend.service.kafka")
@Import(value = [KafkaConfiguration::class])
class KafkaConfiguration {
}