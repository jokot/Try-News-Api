package com.example.trynewsapi.core.network.model

import com.example.trynewsapi.core.model.Source
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkSource(
    @SerialName("category")
    val category: String? = null,
    @SerialName("country")
    val country: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("language")
    val language: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("url")
    val url: String? = null
) {
    fun toDomain() = Source(
        category = category.orEmpty(),
        description = description.orEmpty(),
        id = id.orEmpty(),
        name = name.orEmpty(),
        url = url.orEmpty()
    )
}