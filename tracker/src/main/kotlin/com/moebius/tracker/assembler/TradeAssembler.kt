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
        val tradeDto = TradeDto()
        tradeDto.id = UUID.randomUUID().toString()
        tradeDto.exchange = exchange
        tradeDto.symbol = symbol
        tradeDto.tradeType = askBid
        tradeDto.change = change
        tradeDto.price = tradePrice
        tradeDto.volume = tradeVolume
        tradeDto.prevClosingPrice = prevClosingPrice
        tradeDto.changePrice = changePrice
        tradeDto.createdAt = LocalDateTime.now()
        tradeDto
    }

    fun toTradeDocument(upbitTradeDto: UpbitTradeDto): TradeDocument = with(upbitTradeDto) {
        TradeDocument.of(exchange, symbol, askBid, change, tradePrice, tradeVolume, prevClosingPrice, changePrice)
    }
}