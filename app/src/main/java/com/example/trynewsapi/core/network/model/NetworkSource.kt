package com.example.trynewsapi.core.network.model

import com.example.trynewsapi.core.model.Source
import kotlinx.serialization.Serializable

@Serializable
data class NetworkSource(
    val id: String? = null,
    val name: String? = null
)

fun NetworkSource.asExternalModel() =
    Source(
        id = id.orEmpty(),
        name = name.orEmpty()
    )
