package com.moebius.api.repository;

import com.moebius.api.aggregation.TradeStatsAggregationQuery;
import com.moebius.api.dto.TradeAggregationRequest;
import com.moebius.api.entity.TradeStatsAggregation;
import com.moebius.api.mapper.TradeStatsAggregationMapper;
import com.moebius.data.DocumentIndex;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TradeStatsAggregationRepository {
    private final RestHighLevelClient client;
    private final TradeStatsAggregationQuery tradeStatsAggregationQuery;
    private final TradeStatsAggregationMapper tradeStatsAggregationMapper;

    public List<TradeStatsAggregation> findTradeStatsAggregation(TradeAggregationRequest request) {
        final var query = tradeStatsAggregationQuery.getQuery(request);
        final var searchRequest = getSearchRequest(DocumentIndex.TRADE_STAT, query);
        return search(searchRequest);
    }

    private SearchRequest getSearchRequest(String index, SearchSourceBuilder builder) {
        return new SearchRequest(index).source(builder);
    }

    private List<TradeStatsAggregation> search(SearchRequest request) {
        try {
            final var response = client.search(request, RequestOptions.DEFAULT);
            return tradeStatsAggregationMapper.map(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
