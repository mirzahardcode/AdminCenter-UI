package com.mirzadev.admincenter.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mirzadev.admincenter.data.model.SessionState
import com.mirzadev.admincenter.ui.LocalAppContainer
import com.mirzadev.admincenter.ui.auth.LoginScreen
import com.mirzadev.admincenter.ui.components.LoadingView
import com.mirzadev.admincenter.ui.containerViewModel

@Composable
fun AdminCenterRoot() {
    val container = LocalAppContainer.current
    val session by container.adminAuthRepository.session.collectAsStateWithLifecycle()
    val holder = containerViewModel { SessionScopeHolder() }

    LaunchedEffect(session) {
        if (session == SessionState.SignedOut) holder.clear()
    }

    when (val state = session) {
        SessionState.Loading -> LoadingView()
        SessionState.SignedOut -> LoginScreen()
        is SessionState.Authorized -> {
            val uid = state.admin.uid
            val owner = remember(uid) { holder.ownerFor(uid) }
            key(uid) {
                CompositionLocalProvider(LocalViewModelStoreOwner provides owner) {
                    AdminNavHost(
                        admin = state.admin,
                        onSignOut = container.adminAuthRepository::signOut
                    )
                }
            }
        }
    }
}
