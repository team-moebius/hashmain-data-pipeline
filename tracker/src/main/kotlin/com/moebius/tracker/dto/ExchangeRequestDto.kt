package com.moebius.tracker.dto

import com.fasterxml.jackson.annotation.JsonFormat


@JsonFormat(shape = JsonFormat.Shape.ARRAY)
data class ExchangeRequestDto(
        val ticket: Ticket,
        val typeCodes: TypeCodes,
        val format: Format
) {

    constructor(symbols: List<String>) : this(Ticket, TypeCodes(codes = symbols), Format)

    object Ticket {
        val ticket: String = "moebius-tracker"
    }

    data class TypeCodes(
            val type: String = "trade",
            val codes: List<String>
    )

    object Format {
        val format: String = "SIMPLE"
    }

}