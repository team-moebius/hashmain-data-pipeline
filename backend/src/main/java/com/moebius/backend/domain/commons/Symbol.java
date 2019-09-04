package com.moebius.backend.domain.commons;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Symbol {
    KRW_BTC("KRW-BTC"),
    KRW_ETH("KRW-ETH"),
    KRW_XRP("KRW-XRP"),
    KRW_EOS("KRW-EOS"),
    KRW_LTC("KRW-LTC"),
    KRW_BCH("KRW-BCH"),
    KRW_TRX("KRW-TRX"),
    KRW_XLM("KRW-XLM"),
    KRW_BSV("KRW-BSV");

    private final String value;

    @JsonValue
    public String value() {
        return value;
    }

    Symbol(String value) {
        this.value = value;
    }
}
