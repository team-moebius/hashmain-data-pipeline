package com.moebius.backend.service.kafka.consumer;

import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.trades.TradeDocument;
import com.moebius.backend.service.exchange.ExchangeService;
import com.moebius.backend.service.exchange.ExchangeServiceFactory;
import com.moebius.backend.service.kafka.KafkaConsumer;
import com.moebius.backend.service.member.ApiKeyService;
import com.moebius.backend.service.order.ExchangeOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;
import reactor.kafka.receiver.ReceiverOffset;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Map;

@Slf4j
@Component
public class TradeKafkaConsumer extends KafkaConsumer<String, TradeDocument> {
	private static final String TRADE_KAFKA_TOPIC = "moebius.trade.upbit";
	private final ExchangeOrderService exchangeOrderService;
	private final ApiKeyService apiKeyService;
	private final ExchangeServiceFactory exchangeServiceFactory;

	public TradeKafkaConsumer(Map<String, String> receiverDefaultProperties, ExchangeOrderService exchangeOrderService,
		ApiKeyService apiKeyService, ExchangeServiceFactory exchangeServiceFactory) {
		super(receiverDefaultProperties);
		this.exchangeOrderService = exchangeOrderService;
		this.apiKeyService = apiKeyService;
		this.exchangeServiceFactory = exchangeServiceFactory;
	}

	@Override
	public String getTopic() {
		return TRADE_KAFKA_TOPIC;
	}

	@Override
	public void processRecord(ReceiverRecord<String, TradeDocument> record) {
		ReceiverOffset offset = record.receiverOffset();

		TradeDocument tradeDocument = record.value();
		Exchange exchange = tradeDocument.getExchange();
		String symbol = tradeDocument.getSymbol();
		double price = tradeDocument.getPrice();

		ExchangeService exchangeService = exchangeServiceFactory.getService(exchange);

		// TODO : add more conditions to request order specifically
		exchangeOrderService.getOrdersByExchangeAndSymbol(exchange, symbol)
			.map(order -> apiKeyService.getApiKeyById(order.getApiKeyId().toHexString())
				.flatMap(apiKey -> exchangeService.getAuthToken(apiKey.getAccessKey(), apiKey.getSecretKey()))
				.flatMap(authToken -> exchangeService.order(authToken, order))
				.subscribe())
			.subscribe();

		offset.acknowledge();
	}

	@Override
	protected Class<?> getKeyDeserializerClass() {
		return StringDeserializer.class;
	}

	@Override
	protected Class<?> getValueDeserializerClass() {
		return JsonDeserializer.class;
	}
}
