package com.mirzadev.admincenter.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mirzadev.admincenter.AppConfig
import com.mirzadev.admincenter.BuildConfig
import com.mirzadev.admincenter.data.model.AdminProfile
import com.mirzadev.admincenter.ui.components.ConfirmDialog
import com.mirzadev.admincenter.ui.components.LabeledValue
import com.mirzadev.admincenter.ui.components.SectionCard
import com.mirzadev.admincenter.ui.theme.AppTheme
import com.mirzadev.admincenter.util.ExpiryDates

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    admin: AdminProfile,
    onSignOut: () -> Unit
) {
    var confirmSignOut by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Settings") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(AppTheme.spacing.screenGutter),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.lg)
        ) {
            SectionCard(title = "Signed in as") {
                LabeledValue("Email", admin.email.ifBlank { "-" })
                LabeledValue("Admin UID", admin.uid)
            }

            SectionCard(title = "App") {
                LabeledValue("Version", BuildConfig.VERSION_NAME)
                LabeledValue(
                    "Trusted backend",
                    if (AppConfig.PRIVILEGED_BACKEND_BASE_URL.isBlank()) {
                        "Not configured (Add/Delete user unavailable)"
                    } else {
                        "Configured"
                    }
                )
                LabeledValue("Expiry timezone", ExpiryDates.ZONE_LABEL)
            }

            OutlinedButton(
                onClick = { confirmSignOut = true },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Sign out") }
        }
    }

    if (confirmSignOut) {
        ConfirmDialog(
            title = "Sign out?",
            message = "You will need to sign in again to manage users.",
            confirmLabel = "Sign out",
            onConfirm = {
                confirmSignOut = false
                onSignOut()
            },
            onDismiss = { confirmSignOut = false }
        )
    }
}
