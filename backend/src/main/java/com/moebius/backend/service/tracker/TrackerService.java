package com.moebius.backend.service.tracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moebius.backend.model.Trade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import static com.moebius.backend.utils.ThreadScheduler.IO;

/**
 * TODO : Extract this service as a independent project like moebius-tracker.
 * This would be connected with message queue (kafka) rather than db directly.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TrackerService implements ApplicationListener<ApplicationReadyEvent> {
	private final WebSocketClient webSocketClient;
	private final String message = "[{\"ticket\":\"moebius-test\"},{\"type\":\"trade\",\"codes\":[\"KRW-BTC\",\"KRW-BCH\",\"KRW-XRP\",\"KRW-EOS\",\"KRW-ETH\"]},{\"format\":\"SIMPLE\"}]";
	@Value("${exchange.upbit.websocket.uri}")
	private String uri;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		trackTrades();
	}

	private void trackTrades() {
		ObjectMapper objectMapper = new ObjectMapper();

		webSocketClient.execute(
			URI.create(uri),
			session -> session.send(Mono.just(session.textMessage(message)))
				.thenMany(session.receive()
					.map(webSocketMessage -> {
						try {
							Trade trade = objectMapper.readValue(webSocketMessage.getPayloadAsText(), Trade.class);
							log.info("[{}] {}", LocalDateTime.ofInstant(Instant.ofEpochMilli(trade.getTradeTimestamp()), TimeZone.getDefault().toZoneId()), trade);
						} catch (IOException e) {
							e.printStackTrace();
						}
						return webSocketMessage;
					}))
				.then())
			.subscribeOn(IO.scheduler())
			.doOnSuccess(aVoid -> trackTrades())
			.log()
			.subscribe();
	}
}