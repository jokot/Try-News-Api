package com.example.trynewsapi.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.trynewsapi.feature.headlines.navigation.navigateToHeadlines
import com.example.trynewsapi.feature.search.navigation.navigateToSearch
import com.example.trynewsapi.feature.source.navigation.navigateToSource
import com.example.trynewsapi.navigation.TopLevelDestination
import kotlin.reflect.KClass

@Composable
fun rememberNewsAppState(
    navController: NavHostController = rememberNavController()
): NewsAppState {
    return remember(navController) {
        NewsAppState(navController)
    }
}

@Stable
class NewsAppState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val topLevelDestinations = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when (topLevelDestination) {
            TopLevelDestination.HEADLINES -> navController.navigateToHeadlines(navOptions)
            TopLevelDestination.SEARCH -> navController.navigateToSearch(navOptions)
            TopLevelDestination.SOURCE -> navController.navigateToSource(navOptions)
        }
    }

    fun isRouteInHierarchies(
        destination: NavDestination?,
        route: KClass<*>
    ): Boolean =
        destination?.hierarchy?.any {
            it.hasRoute(route)
        } ?: false
}