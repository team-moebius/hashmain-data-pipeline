package com.moebius.frontend.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Slf4j
@Configuration
@EnableWebFlux
public class WebFrontConfiguration implements WebFluxConfigurer {
    public WebFrontConfiguration() {
        log.debug("Start to initialize web front configuration ...");
    }
}
