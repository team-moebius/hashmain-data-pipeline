package com.moebius.backend.domain

import com.fasterxml.jackson.annotation.JsonIgnore

interface ElasticDocument {
    @JsonIgnore(true)
    fun getDocumentId(): String
}