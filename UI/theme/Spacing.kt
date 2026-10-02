package com.mirzadev.admincenter.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Spacing scale for AdminCenter.
 *
 * Replaces the ad-hoc dp literals previously scattered across screens
 * (12.dp on Dashboard/Detail, 16.dp on Settings, 24.dp on Login and the
 * state views). Density target is "comfortable but efficient": [md] is the
 * default gap between related elements, [lg] the default screen gutter.
 *
 * Read via MaterialTheme-style access:
 *     Modifier.padding(AppTheme.spacing.lg)
 */
@Immutable
data class Spacing(
    /** 4.dp - hairline gap, label to value. */
    val xs: Dp = 4.dp,
    /** 8.dp - tight gap, chip rows and list item internals. */
    val sm: Dp = 8.dp,
    /** 12.dp - default gap between related elements. */
    val md: Dp = 12.dp,
    /** 16.dp - screen gutter and card interior padding. */
    val lg: Dp = 16.dp,
    /** 24.dp - section separation, centered-state padding. */
    val xl: Dp = 24.dp,
    /** 32.dp - major block separation. */
    val xxl: Dp = 32.dp
) {
    /** Standard horizontal screen gutter. */
    val screenGutter: Dp get() = lg

    /** Interior padding for cards and section containers. */
    val cardPadding: Dp get() = lg

    /**
     * Bottom padding that keeps scrollable content clear of a FAB.
     * Replaces the hardcoded 88.dp in UsersScreen.
     */
    val fabClearance: Dp get() = 88.dp

    /** Minimum touch target per Material accessibility guidance. */
    val minTouchTarget: Dp get() = 48.dp

    /**
     * Maximum content width. Keeps cards readable in landscape instead of
     * stretching edge to edge on a rotated phone.
     */
    val maxContentWidth: Dp get() = 640.dp

    /** Convenience: symmetric screen padding. */
    val screenPadding: PaddingValues
        get() = PaddingValues(horizontal = lg, vertical = md)
}

val LocalSpacing = staticCompositionLocalOf { Spacing() }
