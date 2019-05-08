package com.moebius.backend.configuration;

import com.moebius.backend.domain.Repositories;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
//@EnableMongoAuditing
@EnableReactiveMongoRepositories(basePackageClasses = Repositories.class)
public class MongoConfiguration {
	@Value("${spring.data.mongodb.uri}")
	private String mongoUri;
	@Value("${spring.data.mongodb.database}")
	private String database;

	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create(mongoUri);
	}

	@Bean
	public SimpleReactiveMongoDatabaseFactory reactiveMongoDatabaseFactory(MongoClient mongoClient) {
		return new SimpleReactiveMongoDatabaseFactory(mongoClient, database);
	}

	@Bean
	public ReactiveMongoTemplate reactiveMongoTemplate(ReactiveMongoDatabaseFactory mongoDatabaseFactory) {
		ReactiveMongoTemplate template = new ReactiveMongoTemplate(mongoDatabaseFactory);
		MappingMongoConverter converter = (MappingMongoConverter) template.getConverter();
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));

		return template;
	}
}
