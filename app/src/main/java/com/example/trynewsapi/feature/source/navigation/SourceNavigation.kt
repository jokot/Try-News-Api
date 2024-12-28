package com.example.trynewsapi.feature.source.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.trynewsapi.feature.source.SourceScreen
import kotlinx.serialization.Serializable

@Serializable
data object SourceRoute

fun NavController.navigateToSource(navOptions: NavOptions? = null) =
    navigate(SourceRoute, navOptions)

fun NavGraphBuilder.sourceScreen() {
    composable<SourceRoute> {
        SourceScreen()
    }
}