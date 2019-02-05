package com.moebius.batch.tracker.job.account;

import com.moebius.batch.tracker.dto.Assets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.support.AbstractItemStreamItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@StepScope
public class AssetsWriter extends AbstractItemStreamItemWriter<Map<String, Assets>> {
	@Override
	public void write(List<? extends Map<String, Assets>> items) throws Exception {
		log.warn("Need to write assets to somewhere such as db. Refer the data specification below.");
		items.forEach(item -> log.info(item.toString()));
	}
}
