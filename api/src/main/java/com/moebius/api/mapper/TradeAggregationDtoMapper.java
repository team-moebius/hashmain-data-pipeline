package com.moebius.api.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moebius.api.dto.TradeAggregationDto;
import com.moebius.api.dto.TradeAggregationRequest;
import com.moebius.data.type.Exchange;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.filter.ParsedFilter;
import org.elasticsearch.search.aggregations.metrics.ParsedMax;
import org.elasticsearch.search.aggregations.metrics.ParsedMin;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Map;
import java.util.stream.Collectors;

import static com.moebius.api.factory.TradeAggregationQueryFactory.AGGREGATION_CONTEXT_NAME;

@Component
@RequiredArgsConstructor
public class TradeAggregationDtoMapper {

    private final ObjectMapper objectMapper;

    public TradeAggregationDto map(SearchResponse response, TradeAggregationRequest request){
        ParsedFilter parsedFilter = response.getAggregations().get(AGGREGATION_CONTEXT_NAME);

        long docCount = parsedFilter.getDocCount();
        if(docCount == 0){
            return TradeAggregationDto.builder()
                    .exchange(request.getExchange())
                    .symbol(request.getSymbol())
                    .startAt(request.getFrom().atZone(ZoneId.systemDefault()))
                    .endAt(request.getTo().atZone(ZoneId.systemDefault()))
                    .build();
        }

        //TODO: refactoring
        Map<String, Object> map = parsedFilter.getAggregations().getAsMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> getValueFrom(e.getValue())));

        map.put("exchange", request.getExchange());
        map.put("symbol", request.getSymbol());
        map.put("docCount", docCount);
        return objectMapper.convertValue(map, TradeAggregationDto.class);
    }

    private Object getValueFrom(Aggregation aggregation) {
        // TODO: refactoring
        if (aggregation instanceof ParsedSum) {
            return ((ParsedSum) aggregation).getValue();
        } else if (aggregation instanceof ParsedMax) {
            return ((ParsedMax) aggregation).getValueAsString();
        } else if (aggregation instanceof ParsedMin) {
            return ((ParsedMin) aggregation).getValueAsString();
        }
        return null;
    }
}
