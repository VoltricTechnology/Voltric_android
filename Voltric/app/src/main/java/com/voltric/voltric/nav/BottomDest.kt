package com.voltric.voltric.nav

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.voltric.voltric.R
import com.voltric.voltric.ui.theme.orange

sealed class BottomDest(
    val route: String,
    val label: String,
    val iconRes: Int
) {
    data object Home     : BottomDest("home",     "Home",     R.drawable.home)
    data object MyCars   : BottomDest("mycars",   "Charging",  R.drawable.charging)
    data object Services : BottomDest("services", "Subscription", R.drawable.my_car)
    data object Insights : BottomDest("insights", "Insights", R.drawable.insights)
    data object More     : BottomDest("more",     "More",     R.drawable.more)
}

private val bottomItems = listOf(
    BottomDest.Home, BottomDest.MyCars, BottomDest.Services, BottomDest.Insights, BottomDest.More
)

@Composable
fun VoltricBottomBar(navController: NavController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDest = backStackEntry?.destination

    NavigationBar(
        containerColor = Color.White,
        contentColor = orange,
        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars) // sits above gesture bar
    ) {
        bottomItems.forEach { item ->
            val selected = currentDest.isInHierarchy(item.route)

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(painterResource(item.iconRes), contentDescription = item.label) },
                label = { Text(item.label, fontSize = 10.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = orange,
                    selectedTextColor   = orange,
                    unselectedIconColor = MaterialTheme.colorScheme.outline,
                    unselectedTextColor = MaterialTheme.colorScheme.outline,
                    indicatorColor      = Color.Transparent // keep background flat like the mock
                )
            )
        }
    }
}

private fun NavDestination?.isInHierarchy(route: String): Boolean =
    this?.hierarchy?.any { it.route == route } == true
