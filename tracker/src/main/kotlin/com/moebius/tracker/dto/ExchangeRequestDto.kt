package com.moebius.tracker.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.moebius.backend.domain.commons.Symbol


@JsonFormat(shape = JsonFormat.Shape.ARRAY)
data class ExchangeRequestDto(
        val ticket: Ticket,
        val typeCodes: TypeCodes,
        val format: Format
) {

    constructor(symbols: List<Symbol>) : this(Ticket, TypeCodes(codes = symbols), Format)

    object Ticket {
        val ticket: String = "moebius-tracker"
    }

    data class TypeCodes(
            val type: String = "trade",
            val codes: List<Symbol>
    )

    object Format {
        val format: String = "SIMPLE"
    }

}