package com.moebius.backend.service.tracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moebius.backend.assembler.TradeAssembler;
import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.markets.MarketRepository;
import com.moebius.backend.domain.trades.Trade;
import com.moebius.backend.domain.trades.TradeRepository;
import com.moebius.backend.dto.exchange.TradeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import static com.moebius.backend.utils.ThreadScheduler.COMPUTE;
import static com.moebius.backend.utils.ThreadScheduler.IO;

/**
 * TODO : Extract this service as a independent project as moebius-tracker.
 * This would be connected with message queue (kafka) or cache (redis) rather than db directly.
 */
@Slf4j
@Service
@Profile("!production")
@RequiredArgsConstructor
public class TrackerService implements ApplicationListener<ApplicationReadyEvent> {
	private final WebSocketClient webSocketClient;
	private final TradeRepository tradeRepository;
	private final MarketRepository marketRepository;
	private final TradeAssembler tradeAssembler;
	private final Map<String, WebSocketSession> openedSessions;
	@Value("${exchange.upbit.websocket.uri}")
	private String uri;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		trackTrades();
	}

	public Mono<Void> reTrackTrades() {
		return Mono.defer(() -> {
			openedSessions.forEach((sessionId, session) -> {
				log.info("[Tracker] Try to close session. [id : {}]", sessionId);
				session.close().subscribe();
			});
			openedSessions.clear();
			trackTrades();
			return Mono.empty();
		}).subscribeOn(COMPUTE.scheduler())
			.then();
	}

	private void trackTrades() {
		ObjectMapper objectMapper = new ObjectMapper();

		marketRepository.findAllByExchange(Exchange.UPBIT)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.map(market -> "\"" + market.getSymbol().toString() + "\"")
			.reduce((prevSymbol, nextSymbol) -> prevSymbol + "," + nextSymbol)
			.map(rawMessage -> "[{\"ticket\":\"moebius-tracker\"},{\"type\":\"trade\",\"codes\":[" + rawMessage + "]},{\"format\":\"SIMPLE\"}]")
			.map(message -> {
					log.info("[Tracker] Start to track trades. - message : {}", message);
					return webSocketClient.execute(URI.create(uri),
						session -> {
							log.info("[Tracker] Save opened session. [id : {}]", session.getId());
							openedSessions.put(session.getId(), session);
							return session.send(Mono.just(session.textMessage(message)))
								.thenMany(session.receive().map(webSocketMessage -> {
									try {
										TradeDto tradeDto = objectMapper.readValue(webSocketMessage.getPayloadAsText(), TradeDto.class);
										tradeDto.setExchange(Exchange.UPBIT);
										accumulateTrade(tradeDto);
										// maybe need to use upsertTrade rather accumulateTrade.
										// upsertTrade(tradeDto);
									} catch (IOException e) {
										log.error(e.getMessage());
									}
									return webSocketMessage;
								}))
								.then();
						})
						.subscribe();
				}
			).subscribe();
	}

	private void accumulateTrade(TradeDto tradeDto) {
		Mono.fromCallable(() -> tradeAssembler.toTrade(tradeDto))
			.subscribeOn(COMPUTE.scheduler())
			.publishOn(IO.scheduler())
			.flatMap(tradeRepository::save)
			.subscribe();
	}

	private void upsertTrade(TradeDto tradeDto) {
		tradeRepository.findBySymbol(tradeDto.getSymbol())
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.switchIfEmpty(createTrade(tradeDto))
			.flatMap(trade -> updateTrade(trade, tradeDto))
			.log()
			.subscribe();
	}

	private Mono<Trade> createTrade(TradeDto tradeDto) {
		return Mono.defer(() -> Mono.just(tradeAssembler.toTrade(tradeDto))
			.subscribeOn(COMPUTE.scheduler())
			.publishOn(IO.scheduler())
			.flatMap(tradeRepository::save));
	}

	private Mono<Trade> updateTrade(Trade trade, TradeDto tradeDto) {
		return Mono.just(tradeAssembler.toUpdatedTrade(trade, tradeDto))
			.subscribeOn(COMPUTE.scheduler())
			.publishOn(IO.scheduler())
			.flatMap(tradeRepository::save);
	}
}
