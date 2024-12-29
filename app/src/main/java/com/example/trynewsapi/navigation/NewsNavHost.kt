package com.example.trynewsapi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.trynewsapi.feature.headlines.navigation.BaseRoute
import com.example.trynewsapi.feature.headlines.navigation.headlinesScreen
import com.example.trynewsapi.feature.search.navigation.searchScreen
import com.example.trynewsapi.feature.source.navigation.sourceScreen

@Composable
fun NewsNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BaseRoute,
        modifier = modifier
    ) {
        headlinesScreen()

        searchScreen()

        sourceScreen()
    }
}