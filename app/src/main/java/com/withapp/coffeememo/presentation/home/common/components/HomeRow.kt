package com.withapp.coffeememo.presentation.home.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.withapp.coffeememo.presentation.ui.theme.CoffeeMemoAppDefaults

@Composable
fun <T> HomeRow(
    title: String,
    data: List<T>?,
    cardComposable: @Composable (T) -> Unit,
) {
    HomeHeader(text = title)
    Spacer(Modifier.size(CoffeeMemoAppDefaults.Margin.small))
    data?.let {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(CoffeeMemoAppDefaults.Margin.small)
        ) {
            items(it) { item ->
                cardComposable(item)
            }
        }
    }
}