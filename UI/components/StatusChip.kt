package com.mirzadev.admincenter.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.foundation.layout.padding
import com.mirzadev.admincenter.data.model.UserStatus
import com.mirzadev.admincenter.ui.theme.AppTheme
import com.mirzadev.admincenter.ui.theme.PillShape
import com.mirzadev.admincenter.ui.theme.statusLabel

/**
 * Compact pill showing a user's status.
 *
 * Colors come from [AppTheme.status], which resolves light/dark once in the
 * theme, and the label comes from the shared [statusLabel] helper. Status is
 * conveyed by text as well as color, so it does not depend on color alone.
 */
@Composable
fun StatusChip(status: UserStatus, modifier: Modifier = Modifier) {
    val colors = AppTheme.status[status]
    val label = statusLabel(status)

    Surface(
        modifier = modifier.clearAndSetSemantics { contentDescription = "Status: $label" },
        shape = PillShape,
        color = colors.container,
        contentColor = colors.content
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(
                horizontal = AppTheme.spacing.sm + AppTheme.spacing.xs / 2,
                vertical = AppTheme.spacing.xs
            )
        )
    }
}
