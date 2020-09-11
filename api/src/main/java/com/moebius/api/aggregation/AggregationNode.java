package com.moebius.api.aggregation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.aggregations.AggregationBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Getter
@RequiredArgsConstructor
public class AggregationNode {
    private final AggregationBuilder builder;
    private final Map<String, AggregationNode> childMap = new HashMap<>();

    public AggregationNode addChild(AggregationNode node) {
        if (!isDuplicateChild(node.getBuilder().getName())) {
            childMap.put(node.getBuilder().getName(), node);
            builder.subAggregation(node.builder);
        }
        return this;
    }

    public AggregationNode addChild(AggregationBuilder builder) {
        return addChild(new AggregationNode(builder));
    }

    public boolean isDuplicateChild(String name) {
        if (childMap.containsKey(name)) {
            log.warn("duplicate aggregation builder name {}", name);
            return true;
        }
        return false;
    }

    public Optional<AggregationNode> findNodeByName(String name) {
        if (this.builder.getName().equals(name)) {
            return Optional.of(this);
        }
        return childMap.values().stream()
                .map(node -> node.findNodeByName(name))
                .filter(Optional::isPresent).findAny()
                .orElseGet(Optional::empty);
    }
}
