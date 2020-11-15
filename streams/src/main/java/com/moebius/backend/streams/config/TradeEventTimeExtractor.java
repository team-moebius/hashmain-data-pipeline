package com.moebius.backend.streams.config;

import com.moebius.backend.streams.dto.TradeAggsDto;
import com.moebius.backend.streams.dto.TradeDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

public class TradeEventTimeExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {

        if (record.value() instanceof TradeDto) {
            final TradeDto tradeDto = (TradeDto) record.value();
            if (tradeDto != null)
                return tradeDto.getReceivedTime();
        } else if (record.value() instanceof TradeAggsDto) {
            final TradeAggsDto tradeAggsDto = (TradeAggsDto) record.value();
            if (tradeAggsDto != null)
                return tradeAggsDto.getStartDate().toInstant().getEpochSecond() * 1000;
        }
        return record.timestamp();
    }
}
