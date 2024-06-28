package com.withapp.coffeememo.presentation.base.compose

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun HelloComposable(modifier: Modifier = Modifier) {
    Text(text = "Hello Composable")
}

@Preview
@Composable
private fun HelloComposablePreview() {
    HelloComposable()
}