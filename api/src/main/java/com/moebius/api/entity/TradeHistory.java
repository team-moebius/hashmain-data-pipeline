package com.moebius.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(indexName = "trade-stream-search", createIndex = false)
@NoArgsConstructor
@AllArgsConstructor
public class TradeHistory {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String exchange;

    @Field(type = FieldType.Keyword)
    private String symbol;

    @Field(type = FieldType.Keyword)
    private String tradeType;

    @Field(type = FieldType.Keyword)
    private String change;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Double)
    private Double volume;

    @Field(type = FieldType.Double)
    private Double prevClosingPrice;

    @Field(type = FieldType.Double)
    private Double changePrice;

    @Field(type = FieldType.Date)
    private LocalDateTime createdAt;
}