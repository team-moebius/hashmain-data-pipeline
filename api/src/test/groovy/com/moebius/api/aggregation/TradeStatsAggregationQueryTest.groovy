package com.moebius.api.aggregation

import com.moebius.api.dto.TradeAggregationRequest
import com.moebius.data.type.Exchange
import spock.lang.Specification

import java.time.ZoneId
import java.time.ZonedDateTime

class TradeStatsAggregationQueryTest extends Specification {

    def query_test() {
        when:
        def query =
                new TradeStatsAggregationQuery()
                        .getQuery(TradeAggregationRequest.builder()
                                .symbol("symbol")
                                .exchange(Exchange.UPBIT)
                                .interval(1)
                                .from(ZonedDateTime.of(2020, 9, 1, 0, 0, 0,0, ZoneId.of("Asia/Seoul")))
                                .to(ZonedDateTime.of(2020, 9, 1, 0, 0, 0,0, ZoneId.of("Asia/Seoul")))
                                .build())

        then:
        query.toString() == "{\"size\":0,\"aggregations\":{\"filterQuery\":{\"filter\":{\"bool\":{\"filter\":[{\"term\":{\"exchange\":{\"value\":\"UPBIT\",\"boost\":1.0}}},{\"term\":{\"symbol\":{\"value\":\"symbol\",\"boost\":1.0}}},{\"range\":{\"statsDate\":{\"from\":\"2020-09-01T00:00:00+09:00[Asia/Seoul]\",\"to\":\"2020-09-01T00:00:00+09:00[Asia/Seoul]\",\"include_lower\":true,\"include_upper\":false,\"boost\":1.0}}}],\"adjust_pure_negative\":true,\"boost\":1.0}},\"aggregations\":{\"dateHistogram\":{\"date_histogram\":{\"field\":\"statsDate\",\"interval\":\"1m\",\"offset\":0,\"order\":{\"_key\":\"asc\"},\"keyed\":false,\"min_doc_count\":0},\"aggregations\":{\"totalAskCount\":{\"sum\":{\"field\":\"totalAskCount\"}},\"totalAskPrice\":{\"sum\":{\"field\":\"totalAskPrice\"}},\"totalAskVolume\":{\"sum\":{\"field\":\"totalAskVolume\"}},\"totalBidCount\":{\"sum\":{\"field\":\"totalBidCount\"}},\"totalBidPrice\":{\"sum\":{\"field\":\"totalBidPrice\"}},\"totalBidVolume\":{\"sum\":{\"field\":\"totalBidVolume\"}},\"totalTransactionCount\":{\"sum\":{\"field\":\"totalTransactionCount\"}},\"totalTransactionPrice\":{\"sum\":{\"field\":\"totalTransactionPrice\"}},\"totalTransactionVolume\":{\"sum\":{\"field\":\"totalTransactionVolume\"}}}}}}}}"
    }
}
