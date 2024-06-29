package com.withapp.coffeememo.presentation.base.compose

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.withapp.coffeememo.presentation.ui.theme.AppTheme


@Composable
fun HelloComposable(modifier: Modifier = Modifier) {
    AppTheme {
        Button(
            onClick = { /* ... */ },
            modifier = modifier
        ) { Text(text = "hello world") }
    }
}

@Preview
@Composable
private fun HelloComposablePreview() {
    HelloComposable()
}