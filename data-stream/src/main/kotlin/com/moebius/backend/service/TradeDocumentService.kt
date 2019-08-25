package com.moebius.backend.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.moebius.backend.domain.trades.TradeDocument
import com.moebius.backend.utils.Document
import org.apache.http.HttpHost
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.get.GetRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.RangeQueryBuilder
import org.elasticsearch.index.query.TermQueryBuilder
import reactor.core.publisher.Mono
import java.time.LocalDateTime

class TradeDocumentService(host: String) : AutoCloseable {
    private val client: RestHighLevelClient = RestHighLevelClient(RestClient.builder(HttpHost.create(host)))
    private val mapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule())
            .registerModule(Jdk8Module())
            .registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    override fun close() {
        client.close()
    }

    private val index = Document.tradeStream

    private fun <T> (() -> T?).toMono(): Mono<T> = Mono.fromSupplier(this)

    fun get(id: String): TradeDocument? = with(GetRequest(index.searchIndex()).id(id)) {
        client.get(this, RequestOptions.DEFAULT).run {
            if (this.isExists) mapper.readValue(this.sourceAsBytes, TradeDocument::class.java)
            else null
        }
    }

    fun getAsync(id: String): Mono<TradeDocument?> = { get(id) }.toMono()

    fun save(document: TradeDocument): String? = with(indexRequest(document.id, document)) {
        client.index(this, RequestOptions.DEFAULT).run { this.id }
    }

    fun saveAsync(document: TradeDocument): Mono<String> = { save(document) }.toMono()

    fun saveAll(documents: List<TradeDocument>): Boolean = with(BulkRequest()) {
        val request = documents.map { indexRequest(it.id, it) }.toSet()
        this.add(request)
        return client.bulk(this, RequestOptions.DEFAULT).run {
            !this.hasFailures()
        }
    }

    fun saveAllAsync(documents: List<TradeDocument>): Mono<Boolean> = { saveAll(documents) }.toMono()

    private fun getTermQuery(name: String, target: Any?): TermQueryBuilder? {
        return target?.let {
            QueryBuilders.termQuery(name, target)
        }
    }

    private fun getTimeRangeQuery(start: LocalDateTime, end: LocalDateTime): RangeQueryBuilder {
        return QueryBuilders.rangeQuery("createdAt").gte(start).lte(end)
    }

    private fun indexRequest(id: String, data: Any): IndexRequest = with(IndexRequest(index.saveIndex())) {
        this.id(id)
        this.source(mapper.writeValueAsBytes(data), XContentType.JSON)
    }
}