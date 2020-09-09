package com.moebius.api.factory;

import com.moebius.api.dto.TradeAggregationRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Component
public class TradeAggregationQueryFactory {

    public static final String AGGREGATION_CONTEXT_NAME = "condition_filter";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public SearchSourceBuilder generate(TradeAggregationRequest request) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(0);
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.filter(QueryBuilders.termQuery("exchange", request.getExchange()));
        builder.filter(QueryBuilders.termQuery("symbol", request.getSymbol()));
        builder.filter(QueryBuilders.rangeQuery("statsDate")
                .from(request.getFrom().format(formatter))
                .to(request.getTo().format(formatter))
        );

        AggregationBuilder aggregationBuilder = AggregationBuilders.filter(AGGREGATION_CONTEXT_NAME, builder);
        bindSubAggregationQuery(aggregationBuilder);

        searchSourceBuilder.aggregation(aggregationBuilder);

        return searchSourceBuilder;
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
                AggregationBuilders.sum("totalTransactionVolume").field("totalTransactionVolume"),
                AggregationBuilders.min("startAt").field("statsDate"),
                AggregationBuilders.max("endAt").field("statsDate")
        );
        subAggregation.forEach(sourceBuilder::subAggregation);
    }
}
