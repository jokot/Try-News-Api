package com.example.trynewsapi.feature.source.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.trynewsapi.feature.source.SourceDetailPlaceholder
import com.example.trynewsapi.feature.source.SourceScreen
import kotlinx.serialization.Serializable

@Serializable data object SourcePlaceholderRoute

@Serializable
data class SourceRoute(val id: String)

fun NavController.navigateToSource(sourceId: String, navOptions: NavOptions? = null) =
    navigate(SourceRoute(sourceId), navOptions)

fun NavGraphBuilder.sourceScreen(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onSourceClick: (String) -> Unit
) {
    composable<SourceRoute> {
        SourceScreen(showBackButton, onBackClick, onSourceClick)
    }
}

fun NavGraphBuilder.sourceDetailPlaceholder() {
    composable<SourcePlaceholderRoute> {
        SourceDetailPlaceholder()
    }
}