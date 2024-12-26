package com.example.trynewsapi.feature.headlines

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trynewsapi.core.model.SavableSource
import com.example.trynewsapi.core.ui.EmptyState
import com.example.trynewsapi.core.ui.ErrorState
import com.example.trynewsapi.core.ui.LoadingState
import com.example.trynewsapi.core.ui.NewsItem
import com.example.trynewsapi.core.ui.SourcesSection

@Composable
fun HeadlinesScreen(
    viewModel: HeadlinesViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    HeadlinesScreen(
        modifier = modifier,
        uiState = uiState,
        onToggleFollowSource = viewModel::toggleFollowSource
    )
}

@Composable
fun HeadlinesScreen(
    modifier: Modifier = Modifier,
    uiState: HeadlinesUiState,
    onToggleFollowSource: (SavableSource) -> Unit
) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is HeadlinesUiState.Loading -> {
                    LoadingState()
                }

                is HeadlinesUiState.Empty -> {
                    EmptyState()
                }

                is HeadlinesUiState.Error -> {
                    ErrorState(message = uiState.message)
                }

                is HeadlinesUiState.Success -> {
                    LazyColumn(
                        modifier = modifier.fillMaxSize()
                    ) {
                        item {
                            SourcesSection(
                                savableSources = uiState.previewSources,
                                onSeeAllClick = {},
                                onSourceFollowedChanged = onToggleFollowSource
                            )
                        }

                        // All News Section Header
                        item {
                            Text(
                                text = "All News",
                                style = MaterialTheme.typography.headlineLarge,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }

                        // All News Items
                        items(uiState.news) { article ->
                            NewsItem(article = article) {}
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                            )
                        }
                    }
                }
            }
        }
    }
}