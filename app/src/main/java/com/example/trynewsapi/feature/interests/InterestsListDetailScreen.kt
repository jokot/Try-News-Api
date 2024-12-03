package com.example.trynewsapi.feature.interests

import androidx.activity.compose.BackHandler
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldDestinationItem
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.trynewsapi.feature.interests.navigation.DetailPaneNavHostRoute
import com.example.trynewsapi.feature.source.navigation.SourcePlaceholderRoute
import com.example.trynewsapi.feature.source.navigation.SourceRoute
import com.example.trynewsapi.feature.source.navigation.navigateToSource
import com.example.trynewsapi.feature.source.navigation.sourceDetailPlaceholder
import com.example.trynewsapi.feature.source.navigation.sourceScreen
import java.util.UUID

@Composable
fun InterestsListDetailScreen(
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {
    InterestsListDetailScreen(
        selectedSourceId = "",
        onSourceClick = {},
        windowAdaptiveInfo = windowAdaptiveInfo
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun InterestsListDetailScreen(
    selectedSourceId: String?,
    onSourceClick: (String) -> Unit,
    windowAdaptiveInfo: WindowAdaptiveInfo
) {
    val listDetailNavigator = rememberListDetailPaneScaffoldNavigator(
        scaffoldDirective = calculatePaneScaffoldDirective(windowAdaptiveInfo),
        initialDestinationHistory = listOfNotNull(
            ThreePaneScaffoldDestinationItem(ListDetailPaneScaffoldRole.List),
            ThreePaneScaffoldDestinationItem<Nothing>(ListDetailPaneScaffoldRole.Detail).takeIf {
                !selectedSourceId.isNullOrEmpty()
            }
        )
    )
    BackHandler(listDetailNavigator.canNavigateBack()) {
        listDetailNavigator.navigateBack()
    }

    var nestedNavHostStartRoute by remember {
        val route = selectedSourceId?.let { SourceRoute(id = it) } ?: SourcePlaceholderRoute
        mutableStateOf(route)
    }

    var nestedNavKey by rememberSaveable(
        stateSaver = Saver({ it.toString() }, UUID::fromString)
    ) {
        mutableStateOf(UUID.randomUUID())
    }

    val nestedNavController = key(nestedNavKey) {
        rememberNavController()
    }

    fun onSourceClickOpenDetailPane(sourceId: String) {
        onSourceClick(sourceId)
        if (listDetailNavigator.isDetailPaneVisible()) {
            nestedNavController.navigateToSource(sourceId, navOptions = navOptions {
                popUpTo<DetailPaneNavHostRoute>()
            })
        } else {
            nestedNavHostStartRoute = SourceRoute(id = sourceId)
            nestedNavKey = UUID.randomUUID()
        }
        listDetailNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
    }

    ListDetailPaneScaffold(
        directive = listDetailNavigator.scaffoldDirective,
        value = listDetailNavigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                InterestsScreen()
            }
        },
        detailPane = {
            AnimatedPane {
                key(nestedNavKey) {
                    NavHost(
                        navController = nestedNavController,
                        startDestination = nestedNavHostStartRoute,
                        route = DetailPaneNavHostRoute::class
                    ) {
                        sourceScreen(
                            showBackButton = !listDetailNavigator.isListPaneVisible(),
                            onBackClick = listDetailNavigator::navigateBack,
                            onSourceClick = ::onSourceClickOpenDetailPane
                        )
                        sourceDetailPlaceholder()
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun <T> ThreePaneScaffoldNavigator<T>.isListPaneVisible(): Boolean =
    scaffoldValue[ListDetailPaneScaffoldRole.List] == PaneAdaptedValue.Expanded

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun <T> ThreePaneScaffoldNavigator<T>.isDetailPaneVisible(): Boolean =
    scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded