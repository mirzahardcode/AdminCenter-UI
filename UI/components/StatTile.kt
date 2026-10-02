package com.mirzadev.admincenter.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.text.style.TextOverflow
import com.mirzadev.admincenter.data.model.UserStatus
import com.mirzadev.admincenter.ui.theme.AppTheme

/**
 * Single statistic: a large value above its label.
 *
 * Replaces the private StatCard in DashboardScreen. The label is always
 * rendered, so a tile's meaning never depends on its tint alone.
 *
 * When [onClick] is non-null the tile becomes an actionable card with ripple
 * feedback and a button role, and its accessibility description states the
 * action. Passing null keeps the original static presentation.
 */
@Composable
fun StatTile(
    label: String,
    value: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    onClick: (() -> Unit)? = null,
    actionDescription: String? = null
) {
    val colors = CardDefaults.cardColors(
        containerColor = containerColor,
        contentColor = contentColor
    )
    val description = buildString {
        append("$label: $value")
        if (onClick != null && actionDescription != null) {
            append(". ")
            append(actionDescription)
        }
    }

    if (onClick == null) {
        Card(
            modifier = modifier.clearAndSetSemantics { contentDescription = description },
            colors = colors
        ) {
            StatTileBody(label = label, value = value)
        }
    } else {
        Card(
            onClick = onClick,
            modifier = modifier.clearAndSetSemantics {
                contentDescription = description
                role = Role.Button
            },
            colors = colors
        ) {
            StatTileBody(label = label, value = value)
        }
    }
}

@Composable
private fun StatTileBody(label: String, value: Int) {
    Column(
        modifier = Modifier.padding(AppTheme.spacing.cardPadding),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.xs)
    ) {
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.headlineLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

/**
 * Stat tile tinted by a [UserStatus], drawing from the shared status palette
 * so the tile and its matching [StatusChip] can never diverge.
 */
@Composable
fun StatTile(
    label: String,
    value: Int,
    status: UserStatus,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    actionDescription: String? = null
) {
    val colors = AppTheme.status[status]
    StatTile(
        label = label,
        value = value,
        modifier = modifier,
        containerColor = colors.container,
        contentColor = colors.content,
        onClick = onClick,
        actionDescription = actionDescription
    )
}
