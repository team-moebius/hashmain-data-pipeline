package com.moebius.tracker.domain

import org.springframework.data.repository.NoRepositoryBean
import reactor.core.publisher.Mono

@NoRepositoryBean
interface ElasticDocumentRepository<T> {
    fun get(id: String): T?
    fun getAsync(id: String): Mono<T?>
    fun save(document: T): String
    fun saveAsync(document: T): Mono<String>
    fun saveAll(documents: List<T>): Boolean
    fun saveAllAsync(documents: List<T>): Mono<Boolean>
}