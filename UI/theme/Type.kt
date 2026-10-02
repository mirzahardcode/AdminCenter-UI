package com.mirzadev.admincenter.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

/**
 * Typography for AdminCenter.
 *
 * Single point of control for the type system. Every role is stated
 * explicitly rather than inheriting silently from the Material default, so
 * that swapping in San Francisco Medium / Bold later is a change to
 * [AppFontFamily] and the two weight constants below — no screen edits.
 *
 * NOTE: custom fonts are deliberately NOT integrated in this phase.
 * [AppFontFamily] is FontFamily.Default today; the indirection exists so the
 * later swap is mechanical.
 */
private val AppFontFamily = FontFamily.Default

/** Weight used for emphasis (titles, headlines). Maps to SF Medium later. */
private val EmphasisWeight = FontWeight.SemiBold

/** Weight used for strong emphasis (large display numbers). Maps to SF Bold later. */
private val StrongWeight = FontWeight.Bold

/** Weight used for running text. */
private val NormalWeight = FontWeight.Normal

private val base = Typography()

private fun TextStyle.styled(weight: FontWeight): TextStyle =
    copy(fontFamily = AppFontFamily, fontWeight = weight)

val AppTypography = Typography(
    // Display - reserved; not currently used by any screen.
    displayLarge = base.displayLarge.styled(NormalWeight),
    displayMedium = base.displayMedium.styled(NormalWeight),
    displaySmall = base.displaySmall.styled(NormalWeight),

    // Headline - large numeric values (dashboard stat tiles) and page headers.
    headlineLarge = base.headlineLarge.styled(StrongWeight),
    headlineMedium = base.headlineMedium.styled(EmphasisWeight),
    headlineSmall = base.headlineSmall.styled(EmphasisWeight),

    // Title - app bars, card and section titles, list item primary text.
    titleLarge = base.titleLarge.styled(EmphasisWeight),
    titleMedium = base.titleMedium.styled(EmphasisWeight),
    titleSmall = base.titleSmall.styled(EmphasisWeight),

    // Body - field values and running text.
    bodyLarge = base.bodyLarge.styled(NormalWeight),
    bodyMedium = base.bodyMedium.styled(NormalWeight),
    bodySmall = base.bodySmall.styled(NormalWeight),

    // Label - field labels, chips, buttons, metadata.
    labelLarge = base.labelLarge.styled(EmphasisWeight),
    labelMedium = base.labelMedium.styled(EmphasisWeight),
    labelSmall = base.labelSmall.styled(EmphasisWeight)
)
