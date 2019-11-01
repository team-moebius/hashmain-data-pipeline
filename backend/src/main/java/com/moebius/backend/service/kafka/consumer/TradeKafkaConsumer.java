package com.moebius.backend.service.kafka.consumer;

import com.moebius.backend.domain.commons.Exchange;
import com.moebius.backend.domain.trades.TradeDocument;
import com.moebius.backend.service.exchange.ExchangeService;
import com.moebius.backend.service.exchange.ExchangeServiceFactory;
import com.moebius.backend.service.kafka.KafkaConsumer;
import com.moebius.backend.service.order.OrderService;
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
	private final OrderService orderService;
	private final ExchangeServiceFactory exchangeServiceFactory;

	public TradeKafkaConsumer(Map<String, String> receiverDefaultProperties, OrderService orderService,
		ExchangeServiceFactory exchangeServiceFactory) {
		super(receiverDefaultProperties);
		this.orderService = orderService;
		this.exchangeServiceFactory = exchangeServiceFactory;
	}

	@Override
	public String getTopic() {
		return TRADE_KAFKA_TOPIC;
	}

	@Override
	public void processRecord(ReceiverRecord<String, TradeDocument> record) {
		ReceiverOffset offset = record.receiverOffset();
		Exchange exchange = record.value().getExchange();
		String symbol = record.value().getSymbol();

		ExchangeService<?> exchangeService = exchangeServiceFactory.getService(exchange);
		orderService.getOrdersByExchangeAndSymbol(exchange, symbol)
			.map(exchangeService::order)
			.subscribe();

		log.info("Received message: topic-partition={} offset={} timestamp={} key={} value={}\n",
			offset.topicPartition(),
			offset.offset(),
			record.timestamp(),
			record.key(),
			record.value());

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
