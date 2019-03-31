package com.moebius.frontend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Slf4j
@Configuration
@EnableWebFlux
public class FrontendContextLoader implements WebFluxConfigurer {

}
