package com.example.trynewsapi.feature.search

import com.example.trynewsapi.core.model.Article

sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data object Empty : SearchUiState
    data class Success(val news: List<Article>) : SearchUiState

    data class Error(val message: String) : SearchUiState
}