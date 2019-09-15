package com.moebius.backend.domain

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.moebius.backend.domain.commons.DocumentIndex
import com.moebius.backend.utils.ElasticUtils
import com.moebius.backend.utils.ElasticUtils.indexRequest
import mu.KotlinLogging
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.LocalDateTime

open class ElasticDocumentRepositoryImpl<T>(
        open val client: RestHighLevelClient,
        private val index: DocumentIndex.ElasticIndex,
        private val type: Class<T>
) : ElasticDocumentRepository<T> {

    private val log = KotlinLogging.logger {}

    private fun <T> (() -> T).toMono(): Mono<T> = Mono.fromSupplier(this).subscribeOn(Schedulers.parallel()).publishOn(Schedulers.elastic())

    override fun get(id: String): T? = with(SearchRequest(index.searchIndex())) {
        this.source(SearchSourceBuilder().query(QueryBuilders.termQuery("_id", id)))
        val response = client.search(this, RequestOptions.DEFAULT)
        return if (response.hits.totalHits.value > 0) {
            deserializeSource(listOf(response.hits.hits[0].sourceAsString))[0]
        } else {
            null
        }
    }

    protected fun deserializeSource(source: List<String>): List<T> {
        return source.map { ElasticUtils.mapper.readValue(it, type) }.toList()
    }

    override fun getAsync(id: String): Mono<T?> = { get(id) }.toMono()

    override fun save(document: T): String = with(indexRequest(index, (document as ElasticDocument).getDocumentId(), document)) {
        client.index(this, RequestOptions.DEFAULT).run { this.id }
    }

    override fun saveAsync(document: T): Mono<String> = { save(document) }.toMono()

    override fun saveAll(documents: List<T>): Boolean = with(BulkRequest()) {
        if(documents.isEmpty()) return false
        val request = documents.map { indexRequest(index, (it as ElasticDocument).getDocumentId(), it) }.toSet()
        this.add(request)
        return client.bulk(this, RequestOptions.DEFAULT).run {
            !this.hasFailures()
        }
    }

    override fun saveAllAsync(documents: List<T>): Mono<Boolean> = { saveAll(documents) }.toMono()
}