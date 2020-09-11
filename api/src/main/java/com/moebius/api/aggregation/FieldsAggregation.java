package com.moebius.api.aggregation;

import com.moebius.api.annotation.FieldAnnotation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FieldsAggregation {

    public static List<AggregationBuilder> getAggregations(Class<?> type) {
        var fields = type.getDeclaredFields();
        return Arrays.stream(fields).filter(o -> o.isAnnotationPresent(FieldAnnotation.class))
                .map(o -> {
                            var annotation = o.getAnnotation(FieldAnnotation.class);
                            return getBuilder(annotation.type(), o.getName(), annotation.name());
                        }
                ).collect(Collectors.toList());
    }

    private static AggregationBuilder getBuilder(FieldAggregationType type, String fieldName, String defaultAggregationName) {
        var aggregationName = defaultAggregationName.isEmpty() ? fieldName : defaultAggregationName;
        return AggregationBuilders.sum(aggregationName)
                .field(fieldName);
    }
}
