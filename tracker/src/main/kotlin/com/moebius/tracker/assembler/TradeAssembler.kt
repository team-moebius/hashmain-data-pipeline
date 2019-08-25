package com.moebius.tracker.assembler

import com.moebius.backend.domain.trades.TradeDocument
import com.moebius.tracker.dto.TradeDto
import org.springframework.stereotype.Component

@Component
class TradeAssembler {
    fun toTradeDocument(tradeDto: TradeDto): TradeDocument = with(tradeDto) {
        TradeDocument.of(exchange, symbol, askBid, change, tradePrice, tradeVolume, prevClosingPrice, changePrice)
    }
}