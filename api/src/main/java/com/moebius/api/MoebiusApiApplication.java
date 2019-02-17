package com.moebius.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class MoebiusApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoebiusApiApplication.class, args);
    }

    @Bean
    public WebClient webClient() {
        ClientHttpConnector connector = new ReactorClientHttpConnector();

        return WebClient.builder().clientConnector(connector).build();
    }
}
