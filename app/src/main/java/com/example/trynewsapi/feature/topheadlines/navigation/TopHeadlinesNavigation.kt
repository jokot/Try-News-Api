package com.example.trynewsapi.feature.topheadlines.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.trynewsapi.feature.topheadlines.TopHeadlinesScreen
import kotlinx.serialization.Serializable

@Serializable
data object TopHeadlinesRoute

@Serializable
data object TopHeadlinesBaseRoute

fun NavController.navigateToTopHeadlines(navOptions: NavOptions) =
    navigate(TopHeadlinesRoute, navOptions)

fun NavGraphBuilder.topHeadlinesScreen(
    onSourceClick: (String) -> Unit
) {
    navigation<TopHeadlinesBaseRoute>(startDestination = TopHeadlinesRoute) {
        composable<TopHeadlinesRoute> {
            TopHeadlinesScreen(onSourceClick)
        }
    }
}