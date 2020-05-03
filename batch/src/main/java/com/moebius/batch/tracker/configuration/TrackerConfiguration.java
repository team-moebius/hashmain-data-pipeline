package com.moebius.batch.tracker.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.moebius.batch.listener.DefaultJobListener;
import com.moebius.batch.tracker.dto.Assets;
import com.moebius.batch.tracker.job.TrackerJobs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@EnableConfigurationProperties(UpbitProperties.class)
@ComponentScan(basePackageClasses = TrackerJobs.class)
public class TrackerConfiguration extends DefaultBatchConfigurer {
    private static final String JOB_NAME = "trackingAssetsJob";
    private static final String STEP_NAME = "trackingAssetsStep";
    private static final int CHUNK_SIZE = 1;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job trackingAssetsJob(DefaultJobListener defaultJobListener, Step trackingPriceStep) {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .listener(defaultJobListener)
                .start(trackingPriceStep)
                .build();
    }

    @Bean
    @JobScope
    public Step trackingAssetsStep(ItemReader<Assets> assetsReader,
                                  ItemProcessor<Assets, Map<String, Assets>> assetsToCoinProcessor,
                                  ItemWriter<Map<String, Assets>> coinWriter) {
        return stepBuilderFactory.get(STEP_NAME)
                .<Assets, Map<String, Assets>>chunk(CHUNK_SIZE)
                .reader(assetsReader)
                .processor(assetsToCoinProcessor)
                .writer(coinWriter)
                .build();
    }

    @Bean
    public String upbitJwtAuthToken(UpbitProperties upbitProperties) {
        return JWT.create()
            .withClaim("access_key", upbitProperties.getAccessKey())
            .withClaim("nonce", UUID.randomUUID().toString())
            .sign(Algorithm.HMAC256(upbitProperties.getSecretKey()));
    }
}
