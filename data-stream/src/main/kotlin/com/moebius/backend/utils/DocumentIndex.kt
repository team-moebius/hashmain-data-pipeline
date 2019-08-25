package com.moebius.backend.utils

import java.time.LocalDate

object Document {
    val tradeStream: ElasticIndex = ElasticIndex("trade-stream")
}

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