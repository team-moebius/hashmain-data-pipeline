package com.moebius.api.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class TradeHistoryServiceTest extends Specification {

    @Autowired
    TradeHistoryService tradeHistoryService;

    def "FindAllByExchangeAndSymbolAndLatestByCount"() {
        when:
        def result = tradeHistoryService.findAllByExchangeAndSymbolAndLatestByCount("UPBIT", "KRW-EOS", 1)

        then:
        result.size() > 0
    }
}