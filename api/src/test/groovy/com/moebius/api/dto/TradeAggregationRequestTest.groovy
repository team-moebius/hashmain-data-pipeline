package com.moebius.api.dto

import spock.lang.Specification

class TradeAggregationRequestTest extends Specification {

    def builder_test(){

        when:
        def diff = 10
        def a = TradeAggregationRequest.builder().minutesAgo(diff).build();

        then:
        a.getFrom().plusMinutes(diff) == a.getTo()

    }
}
