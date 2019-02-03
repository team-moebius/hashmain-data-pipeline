package com.moebius.batch.tracker.job.step;

import com.moebius.batch.model.Coin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.AbstractItemStreamItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class TrackerWriter extends AbstractItemStreamItemWriter<Coin> {
	@Override
	public void write(List<? extends Coin> items) {
		log.info("TrackerWriter write List<Coin> {}", items);
	}
}
