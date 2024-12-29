package com.example.trynewsapi.core.model

data class SavableSource(
    val source: Source,
    val isFollowing: Boolean = false
)
