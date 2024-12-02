package com.example.trynewsapi.feature.topheadlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trynewsapi.core.data.repository.BookmarkRepository
import com.example.trynewsapi.core.data.repository.NewsRepository
import com.example.trynewsapi.core.model.SavableArticle
import com.example.trynewsapi.core.network.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlinesViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val combinedData: Flow<TopHeadlinesUiState> =
        combine(
            newsRepository.newsStream,
            bookmarkRepository.bookmarksStreams
        ) { networkState, bookmarks ->
            when (networkState) {
                is NetworkState.Loading -> TopHeadlinesUiState.Loading
                is NetworkState.Success -> {
                    val savableArticle = networkState.data.map { article ->
                        val isBookmarked = bookmarks.contains(article.title)
                        SavableArticle(article, isBookmarked)
                    }
                    TopHeadlinesUiState.Success(savableArticle)
                }

                is NetworkState.Error -> TopHeadlinesUiState.Error(networkState.exception.localizedMessage.orEmpty())
            }
        }

    val uiState: StateFlow<TopHeadlinesUiState> =
        combinedData
            .stateIn(
                scope = viewModelScope,
                initialValue = TopHeadlinesUiState.Loading,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000)
            )

    fun toggleBookmark(articleTitle: String, isBookmarked: Boolean) {
        viewModelScope.launch {
            bookmarkRepository.toggleBookmark(articleTitle, isBookmarked)
        }
    }

    fun refresh(country: String? = null) {
        viewModelScope.launch {
            newsRepository.refresh(country)
        }
    }
}