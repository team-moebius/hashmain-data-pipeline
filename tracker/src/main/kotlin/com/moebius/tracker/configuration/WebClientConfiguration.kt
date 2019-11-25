package com.moebius.tracker.configuration

import com.moebius.backend.configuration.WebClientConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(value = [WebClientConfiguration::class])
class WebClientConfiguration