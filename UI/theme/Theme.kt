package com.mirzadev.admincenter.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

/**
 * Light scheme, built on the indigo brand palette.
 *
 * Every role is stated explicitly. Previously only seven roles were set and
 * the rest fell back to Material's purple defaults — which never showed
 * anyway, because dynamic color was enabled and overrode the whole scheme.
 */
private val LightColors = lightColorScheme(
    primary = Indigo50,
    onPrimary = Color.White,
    primaryContainer = Indigo90,
    onPrimaryContainer = Indigo10,
    inversePrimary = Indigo80,

    secondary = SecondaryLight,
    onSecondary = Color.White,
    secondaryContainer = SecondaryContainerLight,
    onSecondaryContainer = OnSecondaryContainerLight,

    tertiary = TertiaryLight,
    onTertiary = Color.White,
    tertiaryContainer = TertiaryContainerLight,
    onTertiaryContainer = OnTertiaryContainerLight,

    background = NeutralLightBackground,
    onBackground = OnNeutralLight,
    surface = NeutralLightSurface,
    onSurface = OnNeutralLight,
    surfaceVariant = NeutralLightSurfaceVariant,
    onSurfaceVariant = OnNeutralLightVariant,
    surfaceContainer = NeutralLightSurfaceContainer,
    surfaceContainerHigh = NeutralLightSurfaceContainer,
    surfaceContainerLow = NeutralLightBackground,

    outline = NeutralLightOutline,
    outlineVariant = NeutralLightOutlineVariant,

    error = ErrorLight,
    onError = Color.White,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight,

    inverseSurface = OnNeutralLight,
    inverseOnSurface = NeutralLightBackground,
    scrim = Color.Black
)

/**
 * Dark scheme. Mirrors the light scheme role for role.
 */
private val DarkColors = darkColorScheme(
    primary = Indigo80,
    onPrimary = Indigo10,
    primaryContainer = Indigo40,
    onPrimaryContainer = Indigo90,
    inversePrimary = Indigo50,

    secondary = SecondaryDark,
    onSecondary = Color(0xFF2C3547),
    secondaryContainer = SecondaryContainerDark,
    onSecondaryContainer = OnSecondaryContainerDark,

    tertiary = TertiaryDark,
    onTertiary = Color(0xFF3B2947),
    tertiaryContainer = TertiaryContainerDark,
    onTertiaryContainer = OnTertiaryContainerDark,

    background = NeutralDarkBackground,
    onBackground = OnNeutralDark,
    surface = NeutralDarkSurface,
    onSurface = OnNeutralDark,
    surfaceVariant = NeutralDarkSurfaceVariant,
    onSurfaceVariant = OnNeutralDarkVariant,
    surfaceContainer = NeutralDarkSurfaceContainer,
    surfaceContainerHigh = NeutralDarkSurfaceContainer,
    surfaceContainerLow = NeutralDarkBackground,

    outline = NeutralDarkOutline,
    outlineVariant = NeutralDarkOutlineVariant,

    error = ErrorDark,
    onError = OnErrorDark,
    errorContainer = ErrorContainerDark,
    onErrorContainer = OnErrorContainerDark,

    inverseSurface = OnNeutralDark,
    inverseOnSurface = NeutralDarkBackground,
    scrim = Color.Black
)

/**
 * Application theme.
 *
 * Dynamic color is OFF by default so AdminCenter renders its own brand
 * palette consistently on every device. The parameter is retained so a
 * preview or future setting can opt in without touching call sites.
 *
 * Alongside MaterialTheme this publishes two AdminCenter-specific token
 * sets — [Spacing] and [StatusPalette] — reachable via [AppTheme].
 */
@Composable
fun AdminCenterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // dynamicColor is accepted for API compatibility and future opt-in, but
    // the branded palette is always used today. Wallpaper theming is not
    // reintroduced here; flipping this on is a deliberate future change.
    val colorScheme = if (darkTheme) DarkColors else LightColors
    val statusPalette = if (darkTheme) DarkStatusPalette else LightStatusPalette

    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalStatusPalette provides statusPalette
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            shapes = AppShapes,
            content = content
        )
    }
}
