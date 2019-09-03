package com.moebius.tracker.configuration

import com.moebius.backend.configuration.MongoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@ComponentScan("com.moebius.backend.domain.markets")
@Import(value = [MongoConfiguration::class])
class MongoConfiguration {
}