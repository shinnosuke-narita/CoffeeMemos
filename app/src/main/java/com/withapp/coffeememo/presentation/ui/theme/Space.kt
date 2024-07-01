package com.withapp.coffeememo.presentation.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal data object CoffeeMemoAppDefaults {
    val IconSize: IconSize = IconSize()
    val Margin: Margin = Margin()
    val Padding: Padding = Padding()
    val BorderWidth: BorderWidth = BorderWidth()
}

internal data class IconSize(
    val default: Dp = 24.dp,
    val medium: Dp = 40.dp,
    val large: Dp  = 48.dp
)

internal data class Margin(
    val small: Dp = 8.dp,
    val medium: Dp = 14.dp,
    val large: Dp  = 20.dp,
    val extraLarge: Dp = 32.dp,
    val extraLargeX: Dp = 64.dp
)

internal data class Padding(
    val small: Dp = 8.dp,
    val medium: Dp = 14.dp,
    val large: Dp  = 20.dp,
    val extraLarge: Dp = 32.dp
)

internal data class BorderWidth(
    val default: Dp = 1.dp
)
