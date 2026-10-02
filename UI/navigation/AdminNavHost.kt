package com.mirzadev.admincenter.ui.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mirzadev.admincenter.data.model.AdminProfile
import com.mirzadev.admincenter.ui.dashboard.DashboardScreen
import com.mirzadev.admincenter.ui.settings.SettingsScreen
import com.mirzadev.admincenter.ui.users.AddUserScreen
import com.mirzadev.admincenter.ui.users.UserDetailScreen
import com.mirzadev.admincenter.ui.users.UserFilter
import com.mirzadev.admincenter.ui.users.UsersScreen

private data class TopLevelTab(val route: String, val label: String, val icon: ImageVector)

private val tabs = listOf(
    TopLevelTab(Routes.DASHBOARD, "Dashboard", Icons.Default.Home),
    TopLevelTab(Routes.USERS, "Users", Icons.Default.Person),
    TopLevelTab(Routes.SETTINGS, "Settings", Icons.Default.Settings)
)

private fun NavHostController.navigateToTab(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun AdminNavHost(
    admin: AdminProfile,
    onSignOut: () -> Unit
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = tabs.any { it.route == currentRoute }

    // One-shot handoff for "open Users filtered by X" from the Dashboard.
    //
    // Deliberately NOT a route argument: Routes.USERS stays exactly "users",
    // so the tab's saveState/restoreState and scroll restoration keep working
    // and no duplicate Users destination is pushed onto the back stack.
    // UsersScreen consumes this once and applies it through the filter state
    // UsersViewModel already owns, so there is no second source of truth.
    var pendingUsersFilter by remember { mutableStateOf<UserFilter?>(null) }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    tabs.forEach { tab ->
                        NavigationBarItem(
                            selected = currentRoute == tab.route,
                            onClick = { navController.navigateToTab(tab.route) },
                            icon = { Icon(tab.icon, contentDescription = null) },
                            label = { Text(tab.label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.DASHBOARD,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            composable(Routes.DASHBOARD) {
                DashboardScreen(
                    onOpenUsers = { filter ->
                        pendingUsersFilter = filter
                        navController.navigateToTab(Routes.USERS)
                    }
                )
            }
            composable(Routes.USERS) {
                UsersScreen(
                    onOpenUser = { uid -> navController.navigate(Routes.userDetail(uid)) },
                    onAddUser = { navController.navigate(Routes.ADD_USER) },
                    pendingFilter = pendingUsersFilter,
                    onPendingFilterHandled = { pendingUsersFilter = null }
                )
            }
            composable(Routes.SETTINGS) {
                SettingsScreen(admin = admin, onSignOut = onSignOut)
            }
            composable(Routes.ADD_USER) {
                AddUserScreen(
                    onBack = { navController.popBackStack() },
                    onCreated = { navController.popBackStack() }
                )
            }
            composable(
                route = Routes.USER_DETAIL,
                arguments = listOf(navArgument(Routes.ARG_UID) { type = NavType.StringType })
            ) { entry ->
                val uid = entry.arguments?.getString(Routes.ARG_UID).orEmpty()
                UserDetailScreen(
                    uid = uid,
                    onBack = { navController.popBackStack() },
                    onDeleted = { navController.popBackStack() }
                )
            }
        }
    }
}
