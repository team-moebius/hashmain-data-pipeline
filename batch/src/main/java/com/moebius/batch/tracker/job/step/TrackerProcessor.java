package com.moebius.batch.tracker.job.step;

import com.moebius.batch.model.Coin;
import com.moebius.batch.tracker.dto.Asset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TrackerProcessor implements ItemProcessor<Asset, Coin> {
	@Override
	public Coin process(Asset item) {
		log.info("TrackerProcesser get Asset {}", item);
		return null;
	}
}
