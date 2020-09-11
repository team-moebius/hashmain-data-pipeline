package com.moebius.api.annotation;

import com.moebius.api.aggregation.FieldAggregationType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FieldAnnotation {
    String name() default "";
    FieldAggregationType type();
}
