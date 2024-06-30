package com.withapp.coffeememo.presentation.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal data object CoffeeMemoAppDefaults {
    val IconSize: IconSize = IconSize()

}

internal data class IconSize(
    val default: Dp = 24.dp,
    val medium: Dp = 40.dp,
    val large: Dp  = 48.dp
)
