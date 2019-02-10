package com.moebius.backend;

import com.moebius.backend.database.Repositories;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Slf4j
@Configuration
@EnableReactiveMongoRepositories(basePackageClasses = Repositories.class)
public class BackendContextLoader extends AbstractReactiveMongoConfiguration {

    @Bean
    @Override
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(); // TODO : change to separated mongo db endpoint later.
    }

    @Override
    protected String getDatabaseName() {
        return null;
    }
}
