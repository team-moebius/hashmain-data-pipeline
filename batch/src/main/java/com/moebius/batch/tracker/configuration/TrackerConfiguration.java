package com.moebius.batch.tracker.configuration;

import com.moebius.batch.model.Coin;
import com.moebius.batch.tracker.dto.Price;
import com.moebius.batch.tracker.job.TrackerJobs;
import com.moebius.batch.tracker.job.step.TrackerReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackageClasses = TrackerJobs.class)
public class TrackerConfiguration {
    private static final String JOB_NAME = "trackingPriceJob";
    private static final String STEP_NAME = "trackingPriceStep";
    private static final int CHUNK_SIZE = 1;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job trackingPriceJob(JobExecutionListener jobExecutionListener, Step trackingPriceStep) {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .listener(jobExecutionListener)
                .start(trackingPriceStep)
                .build();
    }

    @Bean
    public Step trackingPriceStep(ItemReader<Price> priceReader,
                                  ItemProcessor<Price, Coin> priceToCoinProcessor,
                                  ItemWriter<Coin> coinWriter) {
        return stepBuilderFactory.get(STEP_NAME)
                .<Price, Coin>chunk(CHUNK_SIZE)
                .reader(priceReader)
                .processor(priceToCoinProcessor)
                .writer(coinWriter)
                .build();
    }
}
