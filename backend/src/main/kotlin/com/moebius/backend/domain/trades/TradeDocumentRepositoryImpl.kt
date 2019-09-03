package com.moebius.backend.domain.trades

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.moebius.backend.domain.commons.DocumentIndex
import mu.KotlinLogging
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.get.GetRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.RangeQueryBuilder
import org.elasticsearch.index.query.TermQueryBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.LocalDateTime

@Repository
class TradeDocumentRepositoryImpl(
        @Autowired val client: RestHighLevelClient
) : TradeDocumentRepository {
    private val log = KotlinLogging.logger {}
    private val index = DocumentIndex.tradeStream


    private val mapper = ObjectMapper().registerModule(KotlinModule())
            .registerModule(Jdk8Module())
            .registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    private fun <T> (() -> T).toMono(): Mono<T> = Mono.fromSupplier(this).subscribeOn(Schedulers.parallel()).publishOn(Schedulers.elastic())

    override fun get(id: String): TradeDocument? = with(GetRequest(index.searchIndex()).id(id)) {
        client.get(this, RequestOptions.DEFAULT).run {
            if (this.isExists) mapper.readValue(this.sourceAsBytes, TradeDocument::class.java)
            else null
        }
    }

    override fun getAsync(id: String): Mono<TradeDocument?> = { get(id) }.toMono()

    override fun save(document: TradeDocument): String = with(indexRequest(document.id, document)) {
        client.index(this, RequestOptions.DEFAULT).run { this.id }
    }

    override fun saveAsync(document: TradeDocument): Mono<String> = { save(document) }.toMono()

    override fun saveAll(documents: List<TradeDocument>): Boolean = with(BulkRequest()) {
        val request = documents.map { indexRequest(it.id, it) }.toSet()
        this.add(request)
        return client.bulk(this, RequestOptions.DEFAULT).run {
            !this.hasFailures()
        }
    }

    override fun saveAllAsync(documents: List<TradeDocument>): Mono<Boolean> = { saveAll(documents) }.toMono()


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
        this.type("_doc")
        this.source(mapper.writeValueAsBytes(data), XContentType.JSON)
    }
}