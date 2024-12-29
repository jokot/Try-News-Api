package com.example.trynewsapi.feature.headlines.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.trynewsapi.feature.headlines.HeadlinesScreen
import kotlinx.serialization.Serializable

@Serializable
data object HeadlinesRoute

@Serializable
data object BaseRoute

fun NavController.navigateToHeadlines(navOptions: NavOptions? = null) =
    navigate(HeadlinesRoute, navOptions)

fun NavGraphBuilder.headlinesScreen() {
    navigation<BaseRoute>(
        startDestination = HeadlinesRoute,
    ) {
        composable<HeadlinesRoute> {
            HeadlinesScreen()
        }
    }
}