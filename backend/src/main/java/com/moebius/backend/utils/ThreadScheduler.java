package com.moebius.backend.utils;

import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public enum ThreadScheduler {
	IO(Schedulers.elastic()),
	COMPUTE(Schedulers.parallel());

	private Scheduler scheduler;

	ThreadScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public Scheduler scheduler() {
		return scheduler;
	}
}
