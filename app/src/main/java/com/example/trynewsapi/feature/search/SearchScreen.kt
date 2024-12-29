package com.example.trynewsapi.feature.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.trynewsapi.core.model.SavableSource
import com.example.trynewsapi.core.ui.EmptyState
import com.example.trynewsapi.core.ui.ErrorState
import com.example.trynewsapi.core.ui.FilterSourcesBottomSheet
import com.example.trynewsapi.core.ui.IdleState
import com.example.trynewsapi.core.ui.LoadingState
import com.example.trynewsapi.core.ui.NewsItem
import com.example.trynewsapi.core.ui.SearchField
import com.example.trynewsapi.core.ui.SourcesSection
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val query by viewModel.query.collectAsState()
    val previewSources by viewModel.previewSources.collectAsState()
    val tempSources by viewModel.tempSource.collectAsState()

    SearchScreen(
        modifier = modifier,
        uiState = uiState,
        query = query,
        previewSources = previewSources,
        tempSources = tempSources,
        onQueryChange = viewModel::onQueryChanged,
        onSearchClick = viewModel::onSearchTriggered,
        onClearQueryClick = viewModel::onClearQuery,
        onResetSourcesFilterClick = viewModel::onResetSelectedSources,
        onSourceSelected = viewModel::onSourceSelected,
        onUpdateSelectedSource = viewModel::onUpdateSelectedSources,
        onApplyFilterClick = viewModel::onApplyFilterTriggered,
        onBottomSheetDismiss = viewModel::onSyncTempSourcesTriggered
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    uiState: SearchUiState,
    query: String,
    previewSources: List<SavableSource>,
    tempSources: List<SavableSource>,
    onQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onClearQueryClick: () -> Unit,
    onResetSourcesFilterClick: () -> Unit,
    onSourceSelected: (Int, SavableSource) -> Unit,
    onUpdateSelectedSource: (Int, SavableSource) -> Unit,
    onApplyFilterClick: () -> Unit,
    onBottomSheetDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    fun hideBottomSheet() {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                showBottomSheet = false
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SearchField(
            query = query,
            onQueryChange = onQueryChange,
            onSearchClick = onSearchClick,
            onClearClick = onClearQueryClick
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is SearchUiState.Idle -> {
                    IdleState()
                }

                is SearchUiState.Error -> {
                    ErrorState(message = uiState.message)
                }

                else -> {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (previewSources.isNotEmpty()) {
                            SourcesSection(
                                savableSources = previewSources,
                                onSeeAllClick = {
                                    showBottomSheet = true
                                },
                                onSourceFollowedChanged = onSourceSelected
                            )
                        }

                        when (uiState) {
                            is SearchUiState.Loading -> {
                                LoadingState()
                            }

                            is SearchUiState.Success -> {
                                LazyColumn(
                                    modifier = modifier.fillMaxSize()
                                ) {
                                    items(uiState.news) { article ->
                                        NewsItem(article = article) {

                                        }
                                        HorizontalDivider(
                                            modifier = Modifier.padding(horizontal = 16.dp),
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                                        )
                                    }
                                }
                            }

                            else -> {
                                EmptyState()
                            }
                        }
                    }
                }
            }

            if (uiState is SearchUiState.Success && showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        onBottomSheetDismiss()
                        showBottomSheet = false
                    },
                    sheetState = sheetState,
                ) {
                    FilterSourcesBottomSheet(
                        sources = tempSources,
                        onFollowToggle = onUpdateSelectedSource,
                        onResetClick = {
                            hideBottomSheet()
                            onResetSourcesFilterClick()
                        },
                        onApplyClick = {
                            hideBottomSheet()
                            onApplyFilterClick()
                        }
                    )
                }
            }
        }

    }
}