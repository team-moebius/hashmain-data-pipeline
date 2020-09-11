package com.moebius.api.aggregation;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class TradeAggregationQuery {

    public static class Builder {

        private final SearchSourceBuilder searchSourceBuilder;
        private final AggregationNode rootNode;
        private final String rootName;
        private final Set<String> builderNameSet = new HashSet<>();

        public Builder(AggregationBuilder rootBuilder) {
            rootNode = new AggregationNode(rootBuilder);
            rootName = rootBuilder.getName();
            searchSourceBuilder = new SearchSourceBuilder().size(0);
        }

        public Builder ofRoot(AggregationBuilder builder) {
            return ofChild(rootName, builder);
        }

        public Builder ofChild(String parentName, AggregationBuilder builder) {
            if (isDuplicateBuilder(builder.getName())) return this;

            rootNode.findNodeByName(parentName).map(o -> o.addChild(builder));

            return this;
        }

        public Builder ofChild(String parentName, List<AggregationBuilder> builders) {
            rootNode.findNodeByName(parentName).ifPresent(node -> {
                builders.stream().filter(builder -> !isDuplicateBuilder(builder.getName()))
                        .forEach(node::addChild);
            });
            return this;
        }

        private boolean isDuplicateBuilder(String name) {
            if (builderNameSet.contains(name)) {
                log.warn("duplicate aggregation builder name {}", name);
                return true;
            }
            builderNameSet.add(name);
            return false;
        }

        public SearchSourceBuilder build() {
            searchSourceBuilder.aggregation(rootNode.getBuilder());
            return searchSourceBuilder;
        }


    }
}
