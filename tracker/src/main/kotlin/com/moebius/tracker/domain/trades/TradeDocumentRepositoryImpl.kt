package com.moebius.tracker.domain.trades

import com.moebius.tracker.domain.ElasticDocumentRepositoryImpl
import com.moebius.tracker.domain.commons.DocumentIndex
import com.moebius.tracker.utils.ElasticUtils
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class TradeDocumentRepositoryImpl(
        override val restHighLevelClient: RestHighLevelClient
) : TradeDocumentRepository, ElasticDocumentRepositoryImpl<TradeDocument>(restHighLevelClient, DocumentIndex.tradeStream, TradeDocument::class.java) {
    override fun getByDateTimeRange(startDateTime: LocalDateTime, endDateTime: LocalDateTime): List<TradeDocument> {
        val request = ElasticUtils.searchRequest(DocumentIndex.tradeStream,
                listOf(ElasticUtils.dateTimeRangeQuery("createdAt", startDateTime, endDateTime)))
        val response = restHighLevelClient.search(request, RequestOptions.DEFAULT)
        val responseHits = response.hits.hits.map { it.sourceAsString }.toList()

        return deserializeSource(responseHits)
    }
}