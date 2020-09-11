package com.moebius.api.aggregation;

import com.moebius.api.dto.TradeAggregationRequest;
import com.moebius.api.entity.TradeStatsAggregation;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TradeStatsAggregationQuery {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'");
    private static final List<AggregationBuilder> fieldAggregations = FieldsAggregation.getAggregations(TradeStatsAggregation.class);

    public SearchSourceBuilder getQuery(TradeAggregationRequest request) {
        AggregationBuilder dateHistogramBuilder = getDateHistogram(request.getInterval());
        return new TradeAggregationQuery.Builder(filterAggregation(request))
                .ofRoot(dateHistogramBuilder)
                .ofChild(dateHistogramBuilder.getName(), fieldAggregations)
                .build();
    }

    private AggregationBuilder filterAggregation(TradeAggregationRequest request) {
        var builder = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("exchange", request.getExchange()))
                .filter(QueryBuilders.termQuery("symbol", request.getSymbol()))
                .filter(QueryBuilders.rangeQuery("statsDate")
                        .from(request.getFrom().format(formatter))
                        .to(request.getTo().format(formatter), false)
                );

        return AggregationBuilders.filter("filterQuery", builder);
    }

    private AggregationBuilder getDateHistogram(int timeWindow) {
        return AggregationBuilders.dateHistogram("dateHistogram")
                .field("statsDate")
                .dateHistogramInterval(DateHistogramInterval.minutes(timeWindow));
    }
}
