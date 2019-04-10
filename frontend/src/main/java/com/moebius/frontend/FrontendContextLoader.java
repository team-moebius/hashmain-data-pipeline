package com.moebius.frontend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver;

@Slf4j
@Configuration
@EnableWebFlux
@RequiredArgsConstructor
public class FrontendContextLoader implements WebFluxConfigurer {
    private final ThymeleafReactiveViewResolver thymeleafReactiveViewResolver;

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(thymeleafReactiveViewResolver);
    }

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
