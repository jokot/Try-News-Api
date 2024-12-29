package com.example.trynewsapi.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticlesResponse(
    @SerialName("articles")
    val articles: List<NetworkArticle>? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("totalResults")
    val totalResults: Int? = null
)