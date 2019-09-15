package com.moebius.tracker.configuration

import com.moebius.backend.configuration.ElasticsearchConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@ComponentScan("com.moebius.backend.domain.trades")
@Import(value = [ElasticsearchConfiguration::class])
class ElasticsearchConfiguration