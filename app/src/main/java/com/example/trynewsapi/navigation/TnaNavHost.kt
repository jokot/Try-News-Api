package com.example.trynewsapi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.trynewsapi.feature.bookmarks.navigation.bookmarksScreen
import com.example.trynewsapi.feature.interests.navigation.interestsListDetailScreen
import com.example.trynewsapi.feature.interests.navigation.navigateToInterests
import com.example.trynewsapi.feature.search.navigation.searchScreen
import com.example.trynewsapi.feature.source.navigation.navigateToSource
import com.example.trynewsapi.feature.topheadlines.navigation.TopHeadlinesBaseRoute
import com.example.trynewsapi.feature.topheadlines.navigation.topHeadlinesScreen
import com.example.trynewsapi.ui.TnaAppState

@Composable
fun TnaNavHost(
    appState: TnaAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = TopHeadlinesBaseRoute,
        modifier = modifier,
    ) {
        topHeadlinesScreen(
            onSourceClick = navController::navigateToSource
        )
        bookmarksScreen(
            onSourceClick = navController::navigateToInterests,
            onShowSnackbar = onShowSnackbar
        )
        searchScreen(
            onBackClick = navController::popBackStack,
            onInterestsClick = { appState.navigateToTopLevelDestination(TopLevelDestination.INTERESTS) },
            onSourceClick = navController::navigateToInterests
        )
        interestsListDetailScreen()
    }
}