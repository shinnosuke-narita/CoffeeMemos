package com.withapp.coffeememo.presentation.components

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.withapp.coffeememo.presentation.ui.theme.AppTheme

@Composable
fun ThemeComposable(content: @Composable () -> Unit) {
    AppTheme {
        Surface {
            content()
        }
    }
}