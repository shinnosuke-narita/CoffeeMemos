package com.withapp.coffeememo.presentation.home.common.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall
    )
}