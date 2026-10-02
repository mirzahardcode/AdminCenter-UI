package com.mirzadev.admincenter.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.mirzadev.admincenter.data.model.UserStatus

/**
 * Container/content color pair for one status.
 */
@Immutable
data class StatusColorPair(
    val container: Color,
    val content: Color
)

/**
 * Single source of truth for user-status colors.
 *
 * Previously these 12 hex values lived inside StatusChip's StatusColors object
 * and branched on isSystemInDarkTheme() at each call site, which meant the
 * dashboard stat tiles and the status chip could drift apart and neither
 * followed the theme. The palette is now resolved once in [AdminCenterTheme]
 * from the same darkTheme flag that selects the color scheme, and published
 * through [LocalStatusPalette].
 *
 * Colors are unchanged from the original values, so Batch 1 introduces no
 * visual difference in status surfaces.
 */
@Immutable
data class StatusPalette(
    val active: StatusColorPair,
    val expired: StatusColorPair,
    val disabled: StatusColorPair
) {
    /** Resolves the color pair for a given status. */
    operator fun get(status: UserStatus): StatusColorPair = when (status) {
        UserStatus.ACTIVE -> active
        UserStatus.EXPIRED -> expired
        UserStatus.DISABLED -> disabled
    }

    fun container(status: UserStatus): Color = get(status).container

    fun content(status: UserStatus): Color = get(status).content
}

internal val LightStatusPalette = StatusPalette(
    active = StatusColorPair(StatusActiveContainerLight, StatusActiveContentLight),
    expired = StatusColorPair(StatusExpiredContainerLight, StatusExpiredContentLight),
    disabled = StatusColorPair(StatusDisabledContainerLight, StatusDisabledContentLight)
)

internal val DarkStatusPalette = StatusPalette(
    active = StatusColorPair(StatusActiveContainerDark, StatusActiveContentDark),
    expired = StatusColorPair(StatusExpiredContainerDark, StatusExpiredContentDark),
    disabled = StatusColorPair(StatusDisabledContainerDark, StatusDisabledContentDark)
)

val LocalStatusPalette = staticCompositionLocalOf { LightStatusPalette }

/**
 * Human-readable label for a status. Kept alongside the palette so status
 * presentation has one home.
 */
fun statusLabel(status: UserStatus): String = when (status) {
    UserStatus.ACTIVE -> "Active"
    UserStatus.EXPIRED -> "Expired"
    UserStatus.DISABLED -> "Disabled"
}

/**
 * Theme accessors for the tokens that Material's own MaterialTheme does not
 * carry. Usage mirrors MaterialTheme:
 *
 *     AppTheme.spacing.lg
 *     AppTheme.status[UserStatus.ACTIVE].container
 */
object AppTheme {
    val spacing: Spacing
        @Composable @ReadOnlyComposable get() = LocalSpacing.current

    val status: StatusPalette
        @Composable @ReadOnlyComposable get() = LocalStatusPalette.current
}
