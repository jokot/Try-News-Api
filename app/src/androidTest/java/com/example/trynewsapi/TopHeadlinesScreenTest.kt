package com.example.trynewsapi

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.trynewsapi.core.testing.data.savableArticlesTestData
import com.example.trynewsapi.feature.topheadlines.TopHeadlinesScreen
import com.example.trynewsapi.feature.topheadlines.TopHeadlinesUiState
import org.junit.Rule
import org.junit.Test

class TopHeadlinesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun topHeadlinesAreDisplayed() {
        composeTestRule.setContent {
            TopHeadlinesScreen(
                uiState = TopHeadlinesUiState.Success(savableArticlesTestData),
                isRefreshing = false,
                onRefreshNews = {  },
                onItemClick = {},
                onToggleBookmark = {_,_ ->}
            )
        }
        composeTestRule
            .onNodeWithText(savableArticlesTestData[0].article.title)
            .assertExists()
    }
}