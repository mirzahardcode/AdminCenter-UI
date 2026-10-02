package com.mirzadev.admincenter.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Brand and semantic color tokens for AdminCenter.
 *
 * These are raw values only. Nothing here is consumed directly by screens;
 * they are assembled into a ColorScheme in [Theme] and into a
 * [StatusPalette] in StatusPalette.kt. Screens read colors through
 * MaterialTheme.colorScheme or LocalStatusPalette, never from this file.
 */

// ---- Brand: indigo -----------------------------------------------------------

val Indigo10 = Color(0xFF0B1F6B)
val Indigo20 = Color(0xFF16309B)
val Indigo40 = Color(0xFF2544B8)
val Indigo50 = Color(0xFF3B5BDB)
val Indigo80 = Color(0xFFB7C4FF)
val Indigo90 = Color(0xFFDDE3FF)

// ---- Neutral / surface -------------------------------------------------------

val NeutralLightBackground = Color(0xFFFAFAFD)
val NeutralLightSurface = Color(0xFFFAFAFD)
val NeutralLightSurfaceVariant = Color(0xFFE3E4ED)
val NeutralLightSurfaceContainer = Color(0xFFF1F2F8)
val NeutralLightOutline = Color(0xFF757784)
val NeutralLightOutlineVariant = Color(0xFFC6C7D2)
val OnNeutralLight = Color(0xFF1A1B21)
val OnNeutralLightVariant = Color(0xFF464854)

val NeutralDarkBackground = Color(0xFF12131A)
val NeutralDarkSurface = Color(0xFF12131A)
val NeutralDarkSurfaceVariant = Color(0xFF454754)
val NeutralDarkSurfaceContainer = Color(0xFF1E1F28)
val NeutralDarkOutline = Color(0xFF8F919E)
val NeutralDarkOutlineVariant = Color(0xFF454754)
val OnNeutralDark = Color(0xFFE4E4EC)
val OnNeutralDarkVariant = Color(0xFFC6C7D2)

// ---- Secondary / tertiary ----------------------------------------------------

val SecondaryLight = Color(0xFF5B6478)
val SecondaryContainerLight = Color(0xFFDFE2F0)
val OnSecondaryContainerLight = Color(0xFF182033)
val SecondaryDark = Color(0xFFC0C6DC)
val SecondaryContainerDark = Color(0xFF434B5F)
val OnSecondaryContainerDark = Color(0xFFDFE2F0)

val TertiaryLight = Color(0xFF6B5778)
val TertiaryContainerLight = Color(0xFFF2DAFF)
val OnTertiaryContainerLight = Color(0xFF251431)
val TertiaryDark = Color(0xFFD7BEE4)
val TertiaryContainerDark = Color(0xFF523F5F)
val OnTertiaryContainerDark = Color(0xFFF2DAFF)

// ---- Error -------------------------------------------------------------------

val ErrorLight = Color(0xFFB3261E)
val ErrorContainerLight = Color(0xFFF9DEDC)
val OnErrorContainerLight = Color(0xFF410E0B)
val ErrorDark = Color(0xFFFFB4AB)
val OnErrorDark = Color(0xFF690005)
val ErrorContainerDark = Color(0xFF93000A)
val OnErrorContainerDark = Color(0xFFFFDAD6)

// ---- Status: active / expired / disabled -------------------------------------
// Values preserved byte-for-byte from the original StatusColors object so that
// Batch 1 introduces no visual change to status surfaces.

val StatusActiveContainerLight = Color(0xFFD7F2DD)
val StatusActiveContentLight = Color(0xFF0F3D1D)
val StatusActiveContainerDark = Color(0xFF1B4D2B)
val StatusActiveContentDark = Color(0xFFB6F0C4)

val StatusExpiredContainerLight = Color(0xFFFFEBB3)
val StatusExpiredContentLight = Color(0xFF4A3600)
val StatusExpiredContainerDark = Color(0xFF5A4300)
val StatusExpiredContentDark = Color(0xFFFFE08A)

val StatusDisabledContainerLight = Color(0xFFFFDAD6)
val StatusDisabledContentLight = Color(0xFF5C1212)
val StatusDisabledContainerDark = Color(0xFF5C1F1F)
val StatusDisabledContentDark = Color(0xFFFFB4AB)
