package com.moebius.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moebius.api.dto.TradeAggregationDto;
import com.moebius.data.DocumentIndex;
import com.moebius.data.type.Exchange;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.ParsedFilter;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeAggregationService {
    public static final String AGGREGATION_CONTEXT_NAME = "condition_filter";
    private final RestHighLevelClient client;
    private final ObjectMapper objectMapper;

    public TradeAggregationDto getTradeAggregation(Exchange exchange, String symbol, int minutesAgo) {
        SearchRequest searchRequest = new SearchRequest(DocumentIndex.TRADE_STAT.toString());
        generateAggregationQuery(exchange, symbol, minutesAgo, searchRequest);
        try {
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            return mappedToDto(response, exchange, symbol);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private TradeAggregationDto mappedToDto(SearchResponse response, Exchange exchange, String symbol) {
        ParsedFilter parsedFilter = response.getAggregations().get(AGGREGATION_CONTEXT_NAME);
        Map<String, Object> map = parsedFilter.getAggregations().getAsMap().entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(),
                        e -> getValueFrom(e.getValue())));

        map.put("exchange", exchange);
        map.put("symbol", symbol);
        return objectMapper.convertValue(map, TradeAggregationDto.class);
    }

    private double getValueFrom(Aggregation aggregation) {
        ParsedSum parsedSum = (ParsedSum) aggregation;
        return parsedSum.getValue();
    }

    private void generateAggregationQuery(Exchange exchange, String symbol, int minutesAgo, SearchRequest searchRequest) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(0);
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.filter(QueryBuilders.termQuery("exchange", exchange));
        builder.filter(QueryBuilders.termQuery("symbol", symbol));
        builder.filter(QueryBuilders.rangeQuery("statsDate").from(String.format("now-%dm/m", minutesAgo)));

        AggregationBuilder aggregationBuilder = AggregationBuilders.filter(AGGREGATION_CONTEXT_NAME, builder);
        bindSubAggregationQuery(aggregationBuilder);

        searchSourceBuilder.aggregation(aggregationBuilder);
        searchRequest.source(searchSourceBuilder);
    }

    private void bindSubAggregationQuery(AggregationBuilder sourceBuilder) {
        // TODO: refactoring reference for target field using annotation.
        List<AggregationBuilder> subAggregation = Arrays.asList(
                AggregationBuilders.sum("totalAskCount").field("totalAskCount"),
                AggregationBuilders.sum("totalAskPrice").field("totalAskPrice"),
                AggregationBuilders.sum("totalAskVolume").field("totalAskVolume"),
                AggregationBuilders.sum("totalBidCount").field("totalBidCount"),
                AggregationBuilders.sum("totalBidPrice").field("totalBidPrice"),
                AggregationBuilders.sum("totalBidVolume").field("totalBidVolume"),
                AggregationBuilders.sum("totalTransactionCount").field("totalTransactionCount"),
                AggregationBuilders.sum("totalTransactionPrice").field("totalTransactionPrice"),
                AggregationBuilders.sum("totalTransactionVolume").field("totalTransactionVolume")
        );
        subAggregation.forEach(o -> sourceBuilder.subAggregation(o));
    }
}