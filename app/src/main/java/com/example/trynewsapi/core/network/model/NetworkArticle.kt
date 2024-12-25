package com.example.trynewsapi.core.network.model

import com.example.trynewsapi.core.model.Article
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NetworkArticle(
    @SerialName("author")
    val author: String? = null,
    @SerialName("content")
    val content: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("publishedAt")
    val publishedAt: String? = null,
    @SerialName("source")
    val source: Source? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("url")
    val url: String? = null,
    @SerialName("urlToImage")
    val urlToImage: String? = null
) {
    @Serializable
    data class Source(
        @SerialName("id")
        val id: String? = null,
        @SerialName("name")
        val name: String? = null
    )

    fun toDomain() = Article(
        sourceId = source?.id.orEmpty(),
        sourceName = source?.name.orEmpty(),
        author = author.orEmpty(),
        content = content.orEmpty(),
        description = description.orEmpty(),
        publishedAt = publishedAt.orEmpty(),
        title = title.orEmpty(),
        url = url.orEmpty(),
        urlToImage = urlToImage.orEmpty()
    )
}