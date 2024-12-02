package com.example.trynewsapi.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkResponse<T>(
    val articles: T? = null,
    val status: String? = null,
    val totalResults: Int? = null
)