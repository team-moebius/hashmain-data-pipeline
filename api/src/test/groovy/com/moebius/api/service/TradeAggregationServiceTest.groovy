package com.moebius.api.service

import com.moebius.api.dto.TradeAggregationRequest
import com.moebius.data.type.Exchange
import org.junit.Ignore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest()
//TODO: change to unit case
@Ignore
class TradeAggregationServiceTest extends Specification {

    @Autowired
    TradeAggregationService tradeAggregationService;

    def "GetTradeAggregation"() {
        when:
        def request = TradeAggregationRequest.builder()
        .exchange(Exchange.UPBIT)
        .symbol("KRW-SC")
        .minutesAgo(5)
                .build()
        def result = tradeAggregationService.getTradeAggregation(request)

        then:
        result != null
    }

    def "GetTradeAggregation_isEmpty"() {
        when:
        def request = TradeAggregationRequest.builder()
                .exchange(Exchange.UPBIT)
                .symbol("-")
                .minutesAgo(5)
                .build()
        def result = tradeAggregationService.getTradeAggregation(request)

        then:
        result != null
    }
}

