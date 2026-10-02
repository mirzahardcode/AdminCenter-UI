package com.mirzadev.admincenter.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.mirzadev.admincenter.ui.theme.AppTheme

/**
 * Secondary label above a primary value.
 *
 * Replaces the private InfoRow (UserDetailScreen) and SettingRow
 * (SettingsScreen) implementations, which rendered the same construct with
 * inconsistent spacing.
 *
 * Long values such as email addresses and user IDs wrap rather than overflow
 * horizontally; [maxLines] bounds them when a call site needs a fixed height.
 *
 * @param maxLines maximum lines for the value; defaults to unbounded so that
 *        long identifiers stay fully readable
 */
@Composable
fun LabeledValue(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(AppTheme.spacing.xs))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis
        )
    }
}
