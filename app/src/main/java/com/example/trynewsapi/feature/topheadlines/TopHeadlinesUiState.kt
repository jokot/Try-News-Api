package com.example.trynewsapi.feature.topheadlines

import com.example.trynewsapi.core.model.SavableArticle

sealed class TopHeadlinesUiState {
    object Loading: TopHeadlinesUiState()
    data class Success(val topHeadlines: List<SavableArticle>): TopHeadlinesUiState()
    data class Error(val message: String) : TopHeadlinesUiState()
}
