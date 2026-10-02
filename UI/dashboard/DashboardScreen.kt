package com.mirzadev.admincenter.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mirzadev.admincenter.data.model.UserStatus
import com.mirzadev.admincenter.ui.components.ErrorView
import com.mirzadev.admincenter.ui.components.LoadingView
import com.mirzadev.admincenter.ui.components.StatTile
import com.mirzadev.admincenter.ui.containerViewModel
import com.mirzadev.admincenter.ui.theme.AppTheme
import com.mirzadev.admincenter.ui.users.UserFilter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(onOpenUsers: (UserFilter) -> Unit) {
    val viewModel = containerViewModel { DashboardViewModel(it.userRepository) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Refresh is active only during a warm refresh of existing content; a cold
    // load still shows LoadingView, exactly as before.
    val refreshing = (state as? DashboardUiState.Content)?.refreshing == true

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                actions = {
                    IconButton(onClick = viewModel::refresh) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = refreshing,
            onRefresh = viewModel::refresh,
            modifier = Modifier.padding(paddingValues = innerPadding).fillMaxSize()
        ) {
            when (val s = state) {
                DashboardUiState.Loading -> LoadingView()
                is DashboardUiState.Failed -> ErrorView(s.message, onRetry = viewModel::refresh)
                is DashboardUiState.Content -> DashboardContent(s, onOpenUsers)
            }
        }
    }
}

@Composable
private fun DashboardContent(
    state: DashboardUiState.Content,
    onOpenUsers: (UserFilter) -> Unit
) {
    val stats = state.stats
    Box(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = AppTheme.spacing.maxContentWidth)
                .fillMaxWidth()
                .padding(
                    horizontal = AppTheme.spacing.screenGutter,
                    vertical = AppTheme.spacing.md
                ),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.md)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.md)) {
                StatTile(
                    label = "Total users",
                    value = stats.total,
                    modifier = Modifier.weight(1f),
                    onClick = { onOpenUsers(UserFilter.ALL) },
                    actionDescription = "Opens all users"
                )
                StatTile(
                    label = "Active",
                    value = stats.active,
                    status = UserStatus.ACTIVE,
                    modifier = Modifier.weight(1f),
                    onClick = { onOpenUsers(UserFilter.ACTIVE) },
                    actionDescription = "Opens active users"
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.md)) {
                StatTile(
                    label = "Expired",
                    value = stats.expired,
                    status = UserStatus.EXPIRED,
                    modifier = Modifier.weight(1f),
                    onClick = { onOpenUsers(UserFilter.EXPIRED) },
                    actionDescription = "Opens expired users"
                )
                StatTile(
                    label = "Disabled",
                    value = stats.disabled,
                    status = UserStatus.DISABLED,
                    modifier = Modifier.weight(1f),
                    onClick = { onOpenUsers(UserFilter.DISABLED) },
                    actionDescription = "Opens disabled users"
                )
            }

            Text(
                text = "Tap a tile to see those users.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "A user with no expiry date counts as expired. Disabled takes priority over expired.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Button(
                onClick = { onOpenUsers(UserFilter.ALL) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Manage users")
            }
        }
    }
}
