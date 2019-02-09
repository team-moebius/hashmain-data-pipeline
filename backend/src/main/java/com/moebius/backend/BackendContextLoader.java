package com.moebius.backend;

import com.moebius.backend.database.Repositories;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Slf4j
@Configuration
@EnableReactiveMongoRepositories(basePackageClasses = Repositories.class)
public class BackendContextLoader {

}
