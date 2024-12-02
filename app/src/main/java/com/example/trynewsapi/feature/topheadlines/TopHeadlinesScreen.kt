package com.example.trynewsapi.feature.topheadlines

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
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trynewsapi.feature.topheadlines.view.HeadlinesFeedsView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopHeadlinesScreen(
    modifier: Modifier = Modifier,
    viewModel: TopHeadlinesViewModel = viewModel()
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
        onItemClick = {},
        onToggleBookmark = viewModel::toggleBookmark
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopHeadlinesScreen(
    modifier: Modifier = Modifier,
    uiState: TopHeadlinesUiState,
    isRefreshing: Boolean,
    refreshState: PullToRefreshState = rememberPullToRefreshState(),
    onRefreshNews: () -> Unit,
    onItemClick: (String) -> Unit,
    onToggleBookmark: (String, Boolean) -> Unit
) {


    PullToRefreshBox(
        modifier = modifier,
        state = refreshState,
        isRefreshing = isRefreshing,
        onRefresh = onRefreshNews
    ) {
        when (uiState) {
            is TopHeadlinesUiState.Loading -> {
                CircularProgressIndicator()
            }

            is TopHeadlinesUiState.Success -> {
                HeadlinesFeedsView(
                    news = uiState.topHeadlines,
                    onItemClick = onItemClick,
                    onToggleBookmark = onToggleBookmark
                )

            }

            is TopHeadlinesUiState.Error -> {
                Text(uiState.message)
            }
        }
    }
}