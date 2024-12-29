package com.example.trynewsapi.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.trynewsapi.R
import com.example.trynewsapi.feature.headlines.navigation.BaseRoute
import com.example.trynewsapi.feature.headlines.navigation.HeadlinesRoute
import com.example.trynewsapi.feature.search.navigation.SearchRoute
import com.example.trynewsapi.feature.source.navigation.SourceRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val labelId: Int,
    @StringRes val titleId: Int,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route
) {
    HEADLINES(
        selectedIcon = Icons.Rounded.LocalFireDepartment,
        unselectedIcon = Icons.Outlined.LocalFireDepartment,
        labelId = R.string.headlines_label,
        titleId = R.string.headlines_title,
        route = HeadlinesRoute::class,
        baseRoute = BaseRoute::class
    ),
    SEARCH(
        selectedIcon = Icons.Rounded.Search,
        unselectedIcon = Icons.Outlined.Search,
        labelId = R.string.search_label,
        titleId = R.string.search_title,
        route = SearchRoute::class
    ),
    SOURCE(
        selectedIcon = Icons.Rounded.Public,
        unselectedIcon = Icons.Outlined.Public,
        labelId = R.string.source_label,
        titleId = R.string.source_title,
        route = SourceRoute::class
    )
}