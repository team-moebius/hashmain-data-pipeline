package com.moebius.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class DefaultJobListener extends JobExecutionListenerSupport {
	public DefaultJobListener() {
		super();
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		super.afterJob(jobExecution);
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		super.beforeJob(jobExecution);
	}
}
