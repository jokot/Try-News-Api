package com.example.trynewsapi.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.trynewsapi.R
import com.example.trynewsapi.core.designsystem.icon.TnaIcons
import com.example.trynewsapi.feature.bookmarks.navigation.BookmarkRoute
import com.example.trynewsapi.feature.interests.navigation.InterestsRoute
import com.example.trynewsapi.feature.topheadlines.navigation.TopHeadlinesBaseRoute
import com.example.trynewsapi.feature.topheadlines.navigation.TopHeadlinesRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route
) {
    TOP_HEADLINES(
        selectedIcon = TnaIcons.Upcoming,
        unselectedIcon = TnaIcons.UpcomingBorder,
        iconTextId = R.string.feature_topheadlines_title,
        titleTextId = R.string.app_name,
        route = TopHeadlinesRoute::class,
        baseRoute = TopHeadlinesBaseRoute::class
    ),
    BOOKMARKS(
        selectedIcon = TnaIcons.Bookmarks,
        unselectedIcon = TnaIcons.BookmarksBorder,
        iconTextId = R.string.feature_bookmarks_title,
        titleTextId = R.string.feature_bookmarks_title,
        route = BookmarkRoute::class
    ),
    INTERESTS(
        selectedIcon = TnaIcons.Grid3x3,
        unselectedIcon = TnaIcons.Grid3x3,
        iconTextId = R.string.feature_interests_label,
        titleTextId = R.string.feature_interests_title,
        route = InterestsRoute::class
    )
}