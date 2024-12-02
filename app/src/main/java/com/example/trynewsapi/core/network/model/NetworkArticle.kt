package com.example.trynewsapi.core.network.model

import com.example.trynewsapi.core.model.Article
import kotlinx.serialization.Serializable

@Serializable
data class NetworkArticle(
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: NetworkSource? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
)

fun NetworkArticle.asExternalModel() =
    Article(
        author = author.orEmpty(),
        description = description.orEmpty(),
        publishedAt = publishedAt.orEmpty(),
        title = title.orEmpty(),
        url = title.orEmpty(),
        urlToImage = urlToImage.orEmpty(),
        source = source?.asExternalModel()
    )
