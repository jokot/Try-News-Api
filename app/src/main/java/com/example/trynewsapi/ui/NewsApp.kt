package com.example.trynewsapi.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.trynewsapi.navigation.NewsNavHost

@Composable
fun NewsApp(
    modifier: Modifier = Modifier,
    appState: NewsAppState = rememberNewsAppState()
) {
    val currentDestination = appState.currentDestination
    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                appState.topLevelDestinations.forEach { topLevelDestination ->
                    val selected = appState.isRouteInHierarchies(
                        currentDestination,
                        topLevelDestination.baseRoute
                    )
                    NavigationBarItem(
                        selected = selected,
                        icon = {
                            Icon(
                                imageVector = if (selected) {
                                    topLevelDestination.selectedIcon
                                } else {
                                    topLevelDestination.unselectedIcon
                                },
                                contentDescription = stringResource(topLevelDestination.labelId)
                            )
                        },
                        label = {
                            Text(stringResource(topLevelDestination.labelId))
                        },
                        onClick = {
                            appState.navigateToTopLevelDestination(topLevelDestination)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            NewsNavHost(
                navController = appState.navController
            )
        }
    }
}