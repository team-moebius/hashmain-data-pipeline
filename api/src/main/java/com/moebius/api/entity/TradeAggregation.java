package com.moebius.api.entity;

import com.moebius.api.annotation.Aggregation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Document(indexName = "trade-stats-v2", createIndex = false)
@AllArgsConstructor
public class TradeAggregation {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String exchange;

    @Field(type = FieldType.Keyword)
    private String symbol;

    @Field(type = FieldType.Keyword)
    private String timeUnit;

    @Aggregation
    @Field(type = FieldType.Long)
    private Long totalAskCount;

    @Aggregation
    @Field(type = FieldType.Double)
    private Double totalAskPrice;

    @Aggregation
    @Field(type = FieldType.Double)
    private Double totalAskVolume;

    @Aggregation
    @Field(type = FieldType.Long)
    private Long totalBidCount;

    @Aggregation
    @Field(type = FieldType.Double)
    private Double totalBidPrice;

    @Aggregation
    @Field(type = FieldType.Double)
    private Double totalBidVolume;

    @Aggregation
    @Field(type = FieldType.Long)
    private Long totalTransactionCount;

    @Aggregation
    @Field(type = FieldType.Double)
    private Double totalTransactionPrice;

    @Aggregation
    @Field(type = FieldType.Double)
    private Double totalTransactionVolume;

//    @Field(type = FieldType.Date)
//    private String statsDate;
}