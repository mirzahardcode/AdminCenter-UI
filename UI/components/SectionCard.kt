package com.mirzadev.admincenter.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.mirzadev.admincenter.ui.theme.AppTheme

/**
 * Titled information section.
 *
 * Replaces the two near-identical private implementations that previously
 * lived in UserDetailScreen (InfoCard) and SettingsScreen (SettingsCard).
 * A single Card with no nested surfaces: title row, then content.
 *
 * @param title section heading
 * @param trailing optional slot aligned to the end of the title row, used for
 *        a status chip or similar compact indicator
 * @param contentSpacing vertical gap between children; defaults to the
 *        standard related-element gap
 */
@Composable
fun SectionCard(
    title: String,
    modifier: Modifier = Modifier,
    trailing: @Composable (() -> Unit)? = null,
    contentSpacing: Dp = AppTheme.spacing.md,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(AppTheme.spacing.cardPadding),
            verticalArrangement = Arrangement.spacedBy(contentSpacing)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                trailing?.invoke()
            }
            content()
        }
    }
}
