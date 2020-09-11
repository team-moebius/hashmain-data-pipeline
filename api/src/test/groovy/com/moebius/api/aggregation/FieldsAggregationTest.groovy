package com.moebius.api.aggregation


import com.moebius.api.entity.TradeStatsAggregation
import spock.lang.Specification

class FieldsAggregationTest extends Specification {

    def "test"(){
        when:
        def t = FieldsAggregation.getAggregations(TradeStatsAggregation.class)
        then:
        t != null

    }
}
