package com.withapp.coffeememo.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.withapp.coffeememo.R
import com.withapp.coffeememo.presentation.ui.theme.CoffeeMemoAppDefaults

@Composable
fun RatingText(rating: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(
                width = CoffeeMemoAppDefaults.BorderWidth.default,
                color = MaterialTheme.colorScheme.inversePrimary,
                shape = MaterialTheme.shapes.extraLarge
            )
            .padding(CoffeeMemoAppDefaults.Padding.small)
    ) {
        Text(
            text = rating.formatRating(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.inversePrimary
        )
    }
}

@Composable
private fun  String.formatRating(): String {
    val rating = stringResource(id = R.string.rating)
    return String.format("%s %s.0", rating, this)
}

@Preview
@Composable
private fun RatingTextPreview() {
    ThemeComposable {
        RatingText("3")
    }
}