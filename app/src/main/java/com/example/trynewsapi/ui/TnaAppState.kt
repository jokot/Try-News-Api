package com.example.trynewsapi.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.trynewsapi.feature.bookmarks.navigation.navigateToBookmarks
import com.example.trynewsapi.feature.interests.navigation.navigateToInterests
import com.example.trynewsapi.feature.search.navigation.navigateToSearch
import com.example.trynewsapi.feature.topheadlines.navigation.navigateToTopHeadlines
import com.example.trynewsapi.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberTnaAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): TnaAppState {
    return remember(
        navController,
        coroutineScope
    ) {
        TnaAppState(
            navController = navController,
            coroutineScope = coroutineScope
        )
    }
}

@Stable
class TnaAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() {
            return TopLevelDestination.entries.firstOrNull { topLevelDestination ->
                currentDestination?.hasRoute(route = topLevelDestination.route) == true
            }
        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }

                launchSingleTop = true

                restoreState = true
            }

            when (topLevelDestination) {
                TopLevelDestination.TOP_HEADLINES -> navController.navigateToTopHeadlines(
                    topLevelNavOptions
                )

                TopLevelDestination.BOOKMARKS -> navController.navigateToBookmarks(
                    topLevelNavOptions
                )

                TopLevelDestination.INTERESTS -> navController.navigateToInterests(
                    null,
                    topLevelNavOptions
                )
            }
        }
    }

    fun navigateToSearch() = navController.navigateToSearch()

}
