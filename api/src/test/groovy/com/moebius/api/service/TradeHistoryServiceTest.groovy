package com.moebius.api.service

import com.moebius.data.type.Exchange
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Ignore
import spock.lang.Specification

@SpringBootTest
class TradeHistoryServiceTest extends Specification {

    @Autowired
    TradeHistoryService tradeHistoryService;

    @Ignore
    def "getLatestHistory"() {
        when:
        def result = tradeHistoryService.getLatestHistory(Exchange.UPBIT, "KRW-EOS", 1)

        then:
        result.size() > 0
    }
}