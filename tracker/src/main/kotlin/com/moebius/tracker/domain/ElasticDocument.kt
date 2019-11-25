package com.moebius.tracker.domain

import com.fasterxml.jackson.annotation.JsonIgnore

interface ElasticDocument {
    @JsonIgnore(true)
    fun getDocumentId(): String
}