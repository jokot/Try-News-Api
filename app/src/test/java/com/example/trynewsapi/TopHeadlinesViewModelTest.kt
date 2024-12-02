package com.example.trynewsapi

import com.example.trynewsapi.core.network.NetworkState
import com.example.trynewsapi.core.testing.data.networkArticlesTestData
import com.example.trynewsapi.core.testing.repository.TestBookmarkRepository
import com.example.trynewsapi.core.testing.repository.TestNewsRepository
import com.example.trynewsapi.core.testing.utils.MainDispatcherRule
import com.example.trynewsapi.feature.topheadlines.TopHeadlinesUiState
import com.example.trynewsapi.feature.topheadlines.TopHeadlinesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class TopHeadlinesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val newsRepository = TestNewsRepository()
    private val bookmarkRepository = TestBookmarkRepository()

    private lateinit var viewModel: TopHeadlinesViewModel

    @Before
    fun setup() {
        viewModel = TopHeadlinesViewModel(
            bookmarkRepository = bookmarkRepository,
            newsRepository = newsRepository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun whenTopHeadlinesAndBookmarksLoaded_UiStateIsCorrect() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }
        newsRepository.sendNewsResources(NetworkState.Success(networkArticlesTestData))
        bookmarkRepository.toggleBookmark(networkArticlesTestData[0].title.orEmpty(), true)

        val uiState = viewModel.uiState.value
        assertEquals(uiState is TopHeadlinesUiState.Success, true)
        assertEquals(true, (uiState as TopHeadlinesUiState.Success).topHeadlines[0].isBookmarked)

        collectJob.cancel()
    }
}