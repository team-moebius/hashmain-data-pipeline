package com.moebius.api.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class TradeAggregationServiceTest extends Specification {

    @Autowired
    TradeAggregationService tradeAggregationService;

    def "GetTradeAggregation"() {
        when:
        def result = tradeAggregationService.getTradeAggregation("UPBIT", "KRW-SC", 10000)

        then:
        result != null
    }
}

