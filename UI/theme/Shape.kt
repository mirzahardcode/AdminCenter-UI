package com.mirzadev.admincenter.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Explicit shape scale for AdminCenter.
 *
 * Previously MaterialTheme.shapes was left at its default and StatusChip
 * hardcoded RoundedCornerShape(50). These values are close to the Material 3
 * defaults on purpose: Batch 1 establishes the tokens without restyling
 * anything, so existing surfaces keep their current look.
 */
val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

/** Fully rounded shape for chips and pills. Replaces inline RoundedCornerShape(50). */
val PillShape = RoundedCornerShape(percent = 50)
