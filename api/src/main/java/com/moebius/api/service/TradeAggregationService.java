package com.moebius.api.service;

import com.moebius.api.dto.TradeAggregationDto;
import com.moebius.api.dto.TradeAggregationRequest;
import com.moebius.api.factory.TradeAggregationQueryFactory;
import com.moebius.api.mapper.TradeAggregationDtoMapper;
import com.moebius.data.DocumentIndex;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class TradeAggregationService {
    private final RestHighLevelClient client;

    private final TradeAggregationQueryFactory aggregationQueryFactory;
    private final TradeAggregationDtoMapper mapper;

    public TradeAggregationDto getTradeAggregation(TradeAggregationRequest request) {
        try {
            SearchRequest searchRequest = getAggregationSearchRequest(request);

            // TODO: non-blocking
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            return mapper.map(response, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private SearchRequest getAggregationSearchRequest(TradeAggregationRequest request) {
        SearchRequest searchRequest = new SearchRequest(DocumentIndex.TRADE_STAT.toString());
        SearchSourceBuilder searchSourceBuilder = aggregationQueryFactory.generate(request);
        return searchRequest.source(searchSourceBuilder);
    }
}