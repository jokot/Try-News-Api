package com.example.trynewsapi.feature.interests.navigation

import androidx.annotation.Keep
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.trynewsapi.feature.interests.InterestsListDetailScreen
import kotlinx.serialization.Serializable

@Serializable data class InterestsRoute(
    val initialSourceId: String? = null
)

@Keep
@Serializable internal object DetailPaneNavHostRoute

fun NavController.navigateToInterests(
    initialSourceId: String? = null,
    navOptions: NavOptions? = null
) = navigate(InterestsRoute(initialSourceId), navOptions)

fun NavGraphBuilder.interestsListDetailScreen() {
    composable<InterestsRoute> {
        InterestsListDetailScreen()
    }
}

