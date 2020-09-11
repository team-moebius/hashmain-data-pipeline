package com.moebius.data;

public enum DocumentIndex {

    TRADE_HISTORY("trade-stream-search"),
    TRADE_STAT("trade-stats-v2");

    private String index;

    DocumentIndex(String index){
        this.index = index;
    }

    @Override
    public String toString() {
        return index;
    }
}