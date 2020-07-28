package com.moebius.api.entity;

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

    @Field(type = FieldType.Long)
    private Long totalAskCount;

    @Field(type = FieldType.Double)
    private Double totalAskPrice;

    @Field(type = FieldType.Double)
    private Double totalAskVolume;

    @Field(type = FieldType.Long)
    private Long totalBidCount;

    @Field(type = FieldType.Double)
    private Double totalBidPrice;

    @Field(type = FieldType.Double)
    private Double totalBidVolume;

    @Field(type = FieldType.Long)
    private Long totalTransactionCount;

    @Field(type = FieldType.Double)
    private Double totalTransactionPrice;

    @Field(type = FieldType.Double)
    private Double totalTransactionVolume;

//    @Field(type = FieldType.Date)
//    private String statsDate;
}