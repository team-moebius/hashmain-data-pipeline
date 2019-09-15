package com.moebius.backend.domain.trades

import com.moebius.backend.domain.ElasticDocumentRepositoryImpl
import com.moebius.backend.domain.commons.DocumentIndex
import com.moebius.backend.utils.ElasticUtils
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class TradeDocumentRepositoryImpl(
        @Autowired override val client: RestHighLevelClient
) : TradeDocumentRepository, ElasticDocumentRepositoryImpl<TradeDocument>(client, DocumentIndex.tradeStream, TradeDocument::class.java) {
    override fun getByDateTimeRange(startDateTime: LocalDateTime, endDateTime: LocalDateTime): List<TradeDocument> {
        val request = ElasticUtils.searchRequest(DocumentIndex.tradeStream,
                listOf(ElasticUtils.dateTimeRangeQuery("createdAt", startDateTime, endDateTime)))
        val response = client.search(request, RequestOptions.DEFAULT)
        val responseHits = response.hits.hits.map { it.sourceAsString }.toList()

        return deserializeSource(responseHits)
    }
}