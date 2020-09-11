package com.moebius.api.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moebius.api.entity.TradeStatsAggregation;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.filter.ParsedFilter;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.elasticsearch.search.aggregations.metrics.ParsedSingleValueNumericMetricsAggregation;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TradeStatsAggregationMapper {

    private final ObjectMapper objectMapper;

    public List<TradeStatsAggregation> map(SearchResponse response) {
        ParsedFilter parsedFilter = response.getAggregations().get("filterQuery");
        long docCount = parsedFilter.getDocCount();
        if (docCount == 0)
            return Collections.emptyList();
        ParsedDateHistogram parsedDateHistogram = parsedFilter.getAggregations().get("dateHistogram");
        var a = parsedDateHistogram.getBuckets();
        return a.stream().map(o -> {
                    var map = o.getAggregations().getAsMap()
                            .entrySet().stream()
                            .collect(Collectors.toMap(Map.Entry::getKey, e -> getValueFrom(e.getValue())));

                    map.put("timeKey", o.getKey());
                    map.put("docCount", o.getDocCount());
                    return objectMapper.convertValue(map, TradeStatsAggregation.class);
                }
        ).collect(Collectors.toList());
    }

    private Object getValueFrom(Aggregation aggregation) {

        if (aggregation instanceof ParsedSingleValueNumericMetricsAggregation) {
            return ((ParsedSingleValueNumericMetricsAggregation) aggregation).getValueAsString();
        }
        return null;
    }
}
