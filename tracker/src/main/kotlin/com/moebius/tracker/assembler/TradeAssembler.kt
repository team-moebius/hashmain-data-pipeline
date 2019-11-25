package com.moebius.tracker.assembler

import com.moebius.backend.dto.TradeDto
import com.moebius.tracker.domain.trades.TradeDocument
import com.moebius.tracker.dto.upbit.UpbitTradeDto
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class TradeAssembler {
    fun toCommonDto(upbitTradeDto: UpbitTradeDto): TradeDto = with(upbitTradeDto) {
        TradeDto.builder()
                .id(UUID.randomUUID().toString())
                .exchange(exchange)
                .symbol(symbol)
                .tradeType(askBid)
                .change(change)
                .price(tradePrice)
                .volume(tradeVolume)
                .prevClosingPrice(prevClosingPrice)
                .changePrice(changePrice)
                .createdAt(LocalDateTime.now())
                .build()
    }

    fun toTradeDocument(upbitTradeDto: UpbitTradeDto): TradeDocument = with(upbitTradeDto) {
        TradeDocument.of(exchange, symbol, askBid, change, tradePrice, tradeVolume, prevClosingPrice, changePrice)
    }
}