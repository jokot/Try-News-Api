package com.example.trynewsapi.core.ui

import com.example.trynewsapi.core.model.SavableArticle

sealed interface NewsFeedUiState {
    data object Loading: NewsFeedUiState
    data class Success(val topHeadlines: List<SavableArticle>): NewsFeedUiState
    data class Error(val message: String) : NewsFeedUiState
}
