package com.example.trynewsapi.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trynewsapi.core.data.state.DataState
import com.example.trynewsapi.core.domain.model.UseCases
import com.example.trynewsapi.core.model.SavableSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {
    private val _uiState: MutableStateFlow<SearchUiState> =
        MutableStateFlow(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _sources: MutableStateFlow<List<SavableSource>> = MutableStateFlow(emptyList())
    val previewSources: StateFlow<List<SavableSource>> = _sources
        .map { it.take(10) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    private val _tempSources: MutableStateFlow<List<SavableSource>> =
        MutableStateFlow(emptyList())
    val tempSource: StateFlow<List<SavableSource>> = _tempSources.asStateFlow()

    init {
        fetchSources()
        observeQueryChange()
    }

    private fun observeQueryChange() {
        viewModelScope.launch {
            _query.collect {
                if (it.isEmpty()) {
                    _uiState.update { SearchUiState.Idle }
                }
            }
        }
    }

    fun onSearchTriggered() {
        if (_query.value.isEmpty()) return
        searchNews(
            q = _query.value,
            page = 1,
            pageSize = 20,
            sources = _sources.value.toQuery()
        )
    }

    private fun List<SavableSource>.toQuery(): String {
        return this.filter { it.isFollowing }.joinToString(",") { it.source.id }
    }

    fun searchNews(
        q: String,
        page: Int,
        pageSize: Int,
        sources: String
    ) {
        viewModelScope.launch {
            useCases.searchNews(q, page, pageSize, sources).collect { dataState ->
                _uiState.update {
                    when (dataState) {
                        is DataState.Error -> SearchUiState.Error(dataState.message)

                        is DataState.Success -> if (dataState.data.isEmpty()) {
                            SearchUiState.Empty
                        } else {
                            SearchUiState.Success(news = dataState.data)
                        }

                        else -> SearchUiState.Loading
                    }
                }
            }
        }
    }

    private fun fetchSources() {
        viewModelScope.launch {
            useCases.getSources().collect { dataState ->
                when (dataState) {

                    is DataState.Success -> {
                        val savableSources = dataState.data.map { source ->
                            SavableSource(source = source)
                        }
                        _sources.update { savableSources }
                        _tempSources.update { savableSources }
                    }

                    else -> {}
                }
            }
        }
    }

    fun onQueryChanged(query: String) {
        _query.update { query }
    }

    fun onSourceSelected(index: Int, source: SavableSource) {
        val sources = _sources.value.toMutableList()
        sources[index] = source
        _sources.update { sources }
        _tempSources.update { sources }
        onSearchTriggered()
    }

    fun onUpdateSelectedSources(index: Int, source: SavableSource) {
        val sources = _tempSources.value.toMutableList()
        sources[index] = source
        _tempSources.update { sources }
    }

    fun onApplyFilterTriggered() {
        _sources.update { _tempSources.value }
        onSearchTriggered()
    }

    fun onSyncTempSourcesTriggered() {
        _tempSources.update { _sources.value }
    }

    fun onClearQuery() {
        _query.update { "" }
    }

    fun onResetSelectedSources() {
        _sources.update {
            _sources.value.map { it.copy(isFollowing = false) }
        }
        _tempSources.update { _sources.value }
        onSearchTriggered()
    }
}