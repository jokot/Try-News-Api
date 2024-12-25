package com.example.trynewsapi.core.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SourcesResponse(
    @SerialName("sources")
    val sources: List<NetworkSource>? = null,
    @SerialName("status")
    val status: String? = null
)