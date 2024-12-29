package com.example.trynewsapi.feature.headlines

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
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.trynewsapi.R
import com.example.trynewsapi.core.model.SavableSource
import com.example.trynewsapi.core.ui.EmptyState
import com.example.trynewsapi.core.ui.ErrorState
import com.example.trynewsapi.core.ui.FollowingSourcesBottomSheet
import com.example.trynewsapi.core.ui.LoadingState
import com.example.trynewsapi.core.ui.NewsItem
import com.example.trynewsapi.core.ui.SourcesSection

@Composable
fun HeadlinesScreen(
    viewModel: HeadlinesViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    HeadlinesScreen(
        modifier = modifier,
        uiState = uiState,
        onToggleFollowSource = viewModel::toggleFollowSource
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeadlinesScreen(
    modifier: Modifier = Modifier,
    uiState: HeadlinesUiState,
    onToggleFollowSource: (SavableSource) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Text(
            text = stringResource(R.string.headlines_title),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
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
                    Column {
                        SourcesSection(
                            savableSources = uiState.previewSources,
                            onSeeAllClick = {
                                showBottomSheet = true
                            },
                            onSourceFollowedChanged = onToggleFollowSource
                        )
                        LazyColumn(
                            modifier = modifier
                                .weight(1f)
                                .fillMaxWidth()
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
                }
            }

            if (uiState is HeadlinesUiState.Success && showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState,
                ) {
                    FollowingSourcesBottomSheet(
                        sources = uiState.sources,
                        onFollowToggle = onToggleFollowSource
                    )
                }
            }
        }
    }
}