package com.example.trynewsapi.feature.bookmarks.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.trynewsapi.feature.bookmarks.BookmarksScreen
import kotlinx.serialization.Serializable

@Serializable
data object BookmarkRoute

fun NavController.navigateToBookmarks(navOptions: NavOptions) = navigate(BookmarkRoute, navOptions)

fun NavGraphBuilder.bookmarksScreen(
    onSourceClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable<BookmarkRoute> {
        BookmarksScreen(onSourceClick, onShowSnackbar)
    }
}