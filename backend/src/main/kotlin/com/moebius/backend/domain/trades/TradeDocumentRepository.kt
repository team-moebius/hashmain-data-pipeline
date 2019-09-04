package com.moebius.backend.domain.trades

import reactor.core.publisher.Mono

interface TradeDocumentRepository {
    fun get(id: String): TradeDocument?
    fun getAsync(id: String): Mono<TradeDocument?>
    fun save(document: TradeDocument): String
    fun saveAsync(document: TradeDocument): Mono<String>
    fun saveAll(documents: List<TradeDocument>): Boolean
    fun saveAllAsync(document: List<TradeDocument>): Mono<Boolean>
}