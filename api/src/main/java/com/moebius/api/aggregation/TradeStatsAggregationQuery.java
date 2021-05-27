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

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class TradeStatsAggregationQuery {

    private static final List<AggregationBuilder> fieldAggregations = FieldsAggregation.getAggregations(TradeStatsAggregation.class);

    public SearchSourceBuilder getQuery(TradeAggregationRequest request) {
        AggregationBuilder dateHistogramBuilder = getDateHistogram(request);
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
                        .from(request.getFrom().format(DateTimeFormatter.ISO_DATE_TIME))
                        .to(request.getTo()
                                .format(DateTimeFormatter.ISO_DATE_TIME), false)
                );

        return AggregationBuilders.filter("filterQuery", builder);
    }

    private AggregationBuilder getDateHistogram(TradeAggregationRequest request) {
        return AggregationBuilders.dateHistogram("dateHistogram")
                .field("statsDate")
                .minDocCount(1)
                .offset(getDateHistogramOffset(request.getFrom(), request.getInterval()))
                .dateHistogramInterval(DateHistogramInterval.minutes(request.getInterval()));
    }

    private String getDateHistogramOffset(ZonedDateTime from, int interval) {
        long epochSecond = from.toEpochSecond();
        long aggregationStartEpochSecond = Math.floorDiv(epochSecond, TimeUnit.MINUTES.toSeconds(interval)) * TimeUnit.MINUTES.toSeconds(interval);
        long offsetMinutes = epochSecond - aggregationStartEpochSecond;
        return DateHistogramInterval.minutes((int) TimeUnit.SECONDS.toMinutes(offsetMinutes)).toString();
    }
}
