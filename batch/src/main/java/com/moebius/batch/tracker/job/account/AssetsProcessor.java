package com.moebius.batch.tracker.job.account;

import com.moebius.batch.tracker.dto.Assets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@StepScope
public class AssetsProcessor implements ItemProcessor<Assets, Map<String, Assets>> {
	private final String upbitJwtAuthToken;

	public AssetsProcessor(String upbitJwtAuthToken) {
		this.upbitJwtAuthToken = upbitJwtAuthToken;
	}

	@Override
	public Map<String, Assets> process(Assets assets) throws Exception {
		log.info("Start to process the received assets ...");
		Map<String, Assets> assetsMap = new HashMap<>();
		assetsMap.put(upbitJwtAuthToken, assets);

		return assetsMap;
	}
}
