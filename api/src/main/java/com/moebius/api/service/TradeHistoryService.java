package com.moebius.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moebius.api.dto.TradeHistoryDto;
import com.moebius.api.entity.TradeHistory;
import com.moebius.data.DocumentIndex;
import com.moebius.data.type.Exchange;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeHistoryService {

    private final RestHighLevelClient client;
    private final ObjectMapper objectMapper;

    public List<TradeHistoryDto> getLatestHistory(Exchange exchange, String symbol, int count) {
        SearchRequest request = getSearchRequest(exchange, symbol, count);
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            return Arrays.stream(response.getHits().getHits()).map(o -> {
                try {
                    return objectMapper.readValue(o.getSourceAsString(), TradeHistory.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    return null;
                }
            }).map(TradeHistoryDto::fromEntity).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    private SearchRequest getSearchRequest(Exchange exchange, String symbol, int count) {
        SearchRequest request = new SearchRequest(DocumentIndex.TRADE_HISTORY.toString());
        request.source(SearchSourceBuilder.searchSource().query(QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("exchange", exchange))
                .must(QueryBuilders.termQuery("symbol", symbol))
        ).sort("createdAt", SortOrder.DESC).size(count));
        return request;
    }
}

