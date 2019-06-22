package com.moebius.frontend;

import com.moebius.backend.BackendContextLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Slf4j
@Configuration
@ComponentScan(basePackageClasses = BackendContextLoader.class)
@EnableWebFlux
@RequiredArgsConstructor
public class FrontendContextLoader implements WebFluxConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/swagger-ui.html**")
			.addResourceLocations("classpath:/resources/swagger-ui.html");
		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/resources/webjars/");
		registry.addResourceHandler("/static/**")
			.addResourceLocations("classpath:/static/");
	}
}
