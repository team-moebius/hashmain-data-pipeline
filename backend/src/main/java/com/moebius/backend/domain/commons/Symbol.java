package com.moebius.backend.domain.commons;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Symbol {
    @JsonProperty("KRW-BTC")
    KRW_BTC,
    @JsonProperty("KRW-ETH")
    KRW_ETH,
    @JsonProperty("KRW-XRP")
    KRW_XRP,
    @JsonProperty("KRW-EOS")
    KRW_EOS,
    @JsonProperty("KRW-LTC")
    KRW_LTC,
    @JsonProperty("KRW-BCH")
    KRW_BCH,
    @JsonProperty("KRW-BSV")
    KRW_BSV,
    @JsonProperty("KRW-TRX")
    KRW_TRX,
    @JsonProperty("KRW-XLM")
    KRW_XLM;

    @Override
    public String toString() {
        return name().replace("_", "-");
    }
}
