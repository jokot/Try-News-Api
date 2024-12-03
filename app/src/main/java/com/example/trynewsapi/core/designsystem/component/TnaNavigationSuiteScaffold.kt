package com.example.trynewsapi.core.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TnaNavigationSuiteScaffold(
    navigationSuiteItems: TnaNavigationSuiteScope.() -> Unit,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    content: @Composable () -> Unit
) {
    val layoutType = NavigationSuiteScaffoldDefaults
        .calculateFromAdaptiveInfo(windowAdaptiveInfo)
    val navigationSuiteItemColors = NavigationSuiteItemColors(
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = TnaNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = TnaNavigationDefaults.navigationContentColor(),
            selectedTextColor = TnaNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = TnaNavigationDefaults.navigationContentColor(),
            indicatorColor = TnaNavigationDefaults.navigationIndicatorColor()
        ),
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = TnaNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = TnaNavigationDefaults.navigationContentColor(),
            selectedTextColor = TnaNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = TnaNavigationDefaults.navigationContentColor(),
            indicatorColor = TnaNavigationDefaults.navigationIndicatorColor()
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedIconColor = TnaNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = TnaNavigationDefaults.navigationContentColor(),
            selectedTextColor = TnaNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = TnaNavigationDefaults.navigationContentColor()
        )
    )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            TnaNavigationSuiteScope(
                navigationSuiteScope = this,
                navigationSuiteItemColors = navigationSuiteItemColors
            ).run(navigationSuiteItems)
        },
        layoutType = layoutType,
        containerColor = Color.Transparent,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContentColor = TnaNavigationDefaults.navigationContentColor(),
            navigationRailContentColor = Color.Transparent
        ),
        modifier = modifier,
        content = content
    )

}

class TnaNavigationSuiteScope internal constructor(
    private val navigationSuiteScope: NavigationSuiteScope,
    private val navigationSuiteItemColors: NavigationSuiteItemColors
) {
    fun item(
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        icon: @Composable () -> Unit,
        selectedIcon: @Composable () -> Unit,
        label: (@Composable () -> Unit)? = null
    ) = navigationSuiteScope.item(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        label = label,
        colors = navigationSuiteItemColors,
        modifier = modifier
    )
}

object TnaNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}