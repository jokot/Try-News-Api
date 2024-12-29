package com.example.trynewsapi.feature.headlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trynewsapi.core.data.state.DataState
import com.example.trynewsapi.core.domain.model.UseCases
import com.example.trynewsapi.core.model.SavableSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeadlinesViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {
    private val _uiState: MutableStateFlow<HeadlinesUiState> =
        MutableStateFlow(HeadlinesUiState.Loading)
    val uiState: StateFlow<HeadlinesUiState> = _uiState.asStateFlow()

    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            combine(
                useCases.getHeadlines(),
                useCases.getSavableSources()
            ) { newsDataState, sourcesDataState ->
                when {
                    newsDataState is DataState.Error -> HeadlinesUiState.Error(newsDataState.message)
                    sourcesDataState is DataState.Error -> HeadlinesUiState.Error(sourcesDataState.message)

                    newsDataState is DataState.Success
                            && sourcesDataState is DataState.Success -> if (newsDataState.data.isEmpty()) {
                        HeadlinesUiState.Empty
                    } else {
                        HeadlinesUiState.Success(
                            news = newsDataState.data,
                            sources = sourcesDataState.data,
                            previewSources = sourcesDataState.data.subList(0, 10)
                        )
                    }

                    else -> HeadlinesUiState.Loading
                }
            }.collect { state ->
                _uiState.update {
                    state
                }
            }
        }
    }

    fun toggleFollowSource(savableSource: SavableSource) {
        viewModelScope.launch {
            useCases.toggleFollowing(savableSource.source.name, savableSource.isFollowing)
        }
    }
}