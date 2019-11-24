package com.moebius.backend.domain.commons

import java.time.LocalDate

object DocumentIndex {
    val tradeStream: ElasticIndex = ElasticIndex("trade-stream",
            { name -> "$name-${LocalDate.now()}" },
{ name -> "$name-search" })
val tradeStats: ElasticIndex = ElasticIndex("trade-stats")

class ElasticIndex(
        private val name: String,
        private inline val indexDecorator: (String) -> String = { it -> it },
        private inline val searchAlias: (String) -> String = { it -> it }
) {
    fun searchIndex(): String {
        return searchAlias(name)
    }

    fun saveIndex(): String {
        return this.indexDecorator(name)
    }
}

}