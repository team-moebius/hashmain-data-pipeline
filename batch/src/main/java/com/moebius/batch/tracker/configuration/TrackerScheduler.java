package com.moebius.batch.tracker.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class TrackerScheduler {
	private static final long TRACKER_DELAY = 10000;

	private final JobLauncher jobLauncher;
	private final Job trackingAssetsJob;

	@Scheduled(fixedDelay = TRACKER_DELAY)
	public void launch()
		throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
		jobLauncher.run(trackingAssetsJob, new JobParametersBuilder().addDate("executedAt", new Date()).toJobParameters());
	}
}
