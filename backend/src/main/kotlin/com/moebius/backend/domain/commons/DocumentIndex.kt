package com.moebius.backend.domain.commons

import java.time.LocalDate

object DocumentIndex {
    val tradeStream: ElasticIndex = ElasticIndex("trade-stream")

    class ElasticIndex(
            private val name: String
    ) {
        fun searchIndex(): String {
            return "$name-search"
        }

        fun saveIndex(): String {
            return "$name-${LocalDate.now()}"
        }
    }

}