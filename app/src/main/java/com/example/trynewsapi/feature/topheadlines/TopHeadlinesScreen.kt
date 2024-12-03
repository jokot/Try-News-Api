package com.example.trynewsapi.feature.topheadlines

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trynewsapi.core.ui.NewsFeedUiState
import com.example.trynewsapi.core.ui.newsFeed
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopHeadlinesScreen(
    onSourceClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: TopHeadlinesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()
    val refreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            delay(500)
            viewModel.refresh()
            isRefreshing = false
        }
    }

    TopHeadlinesScreen(
        modifier = modifier,
        uiState = uiState,
        isRefreshing = isRefreshing,
        onRefreshNews = onRefresh,
        refreshState = refreshState,
        onSourceClick = onSourceClick,
        onToggleBookmark = viewModel::toggleBookmark
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopHeadlinesScreen(
    modifier: Modifier = Modifier,
    uiState: NewsFeedUiState,
    isRefreshing: Boolean,
    refreshState: PullToRefreshState = rememberPullToRefreshState(),
    onRefreshNews: () -> Unit,
    onSourceClick: (String) -> Unit,
    onToggleBookmark: (String, Boolean) -> Unit
) {
    val state = rememberLazyStaggeredGridState()

    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        state = refreshState,
        isRefreshing = isRefreshing,
        onRefresh = onRefreshNews,
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is NewsFeedUiState.Loading -> {
                CircularProgressIndicator()
            }

            is NewsFeedUiState.Success -> {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Adaptive(300.dp),
                    contentPadding = PaddingValues(16.dp),
                    verticalItemSpacing = 24.dp,
                    state = state
                ) {
                    newsFeed(
                        news = uiState.topHeadlines,
                        onSourceClick = onSourceClick,
                        onToggleBookmark = onToggleBookmark
                    )
                }
            }

            is NewsFeedUiState.Error -> {
                Text(uiState.message)
            }
        }
    }
}