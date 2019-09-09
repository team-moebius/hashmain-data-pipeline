package com.moebius.backend.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.moebius.backend.domain.commons.DocumentIndex
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.aggregations.AggregationBuilder
import org.elasticsearch.search.builder.SearchSourceBuilder
import java.time.LocalDateTime

object ElasticUtils {

    enum class AggregationInterval(val interval: String) {
        EVERY_MINUTES("1m"),
        EVERY_HOURS("1h")

    }

    private const val TIME_ZONE = "GMT+9"

    val mapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule())
            .registerModule(Jdk8Module())
            .registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    /**
     * startDateTime <= x <= endDateTime
     */
    fun dateTimeRangeQuery(field: String, startDateTime: LocalDateTime, endDateTime: LocalDateTime): QueryBuilder {
        // if don't use to string method, query generator converts local date time string to zoned time type string
        return QueryBuilders.rangeQuery(field)
                .timeZone(TIME_ZONE)
                .gte(startDateTime.toString())
                .lte(endDateTime.toString())
    }

    fun indexRequest(index: DocumentIndex.ElasticIndex, id: String, data: Any): IndexRequest = with(IndexRequest(index.saveIndex())) {
        this.id(id)
        this.source(mapper.writeValueAsBytes(data), org.elasticsearch.common.xcontent.XContentType.JSON)
    }

    fun searchRequest(index: DocumentIndex.ElasticIndex, queries: List<QueryBuilder>): SearchRequest {
        val searchFilterQuery = QueryBuilders.boolQuery()
        queries.forEach {
            searchFilterQuery.filter(it)
        }
        return SearchRequest(index.searchIndex())
                .source(SearchSourceBuilder().query(searchFilterQuery))
    }

    /**
     * @param aggregationBuilders
     * 트리 형식의 aggregation query를 조합하여 root aggregation을 리턴한다
     */
    fun aggregationTreeRequest(aggregationBuilders: List<AggregationBuilder>): AggregationBuilder =
            aggregationBuilders.reversed().reduce { acc, it -> it.subAggregation(acc) }

}