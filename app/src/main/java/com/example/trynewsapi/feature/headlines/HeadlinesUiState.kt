package com.example.trynewsapi.feature.headlines

import com.example.trynewsapi.core.model.Article
import com.example.trynewsapi.core.model.SavableSource

sealed interface HeadlinesUiState {
    data object Loading : HeadlinesUiState
    data object Empty : HeadlinesUiState
    data class Success(
        val news: List<Article>,
        val sources: List<SavableSource>,
        val previewSources: List<SavableSource>,
    ) : HeadlinesUiState

    data class Error(val message: String) : HeadlinesUiState
}