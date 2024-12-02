package com.example.trynewsapi.core.model

data class SavableArticle(
    val article: Article,
    val isBookmarked: Boolean
)