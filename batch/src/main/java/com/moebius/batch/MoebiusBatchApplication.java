package com.moebius.batch;

import com.moebius.batch.tracker.configuration.TrackerConfiguration;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.ApplicationContextJobFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MoebiusBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoebiusBatchApplication.class, args);
	}

	@Bean
	public ApplicationContextFactory trackerJobConfig() {
		return new GenericApplicationContextFactory(TrackerConfiguration.class);
	}
}
