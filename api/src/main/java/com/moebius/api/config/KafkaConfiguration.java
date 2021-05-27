package com.moebius.api.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

    @Bean
    public AdminClient kafkaAdminClient(KafkaProperties kafkaProperties){
        var properties = kafkaProperties.buildAdminProperties();
        return KafkaAdminClient.create(properties);
    }
}
