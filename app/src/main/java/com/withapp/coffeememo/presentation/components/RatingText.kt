package com.withapp.coffeememo.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.withapp.coffeememo.R

@Composable
fun RatingText(rating: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(
                width = dimensionResource(id = R.dimen.border_width_small),
                color = MaterialTheme.colorScheme.inversePrimary,
                shape = MaterialTheme.shapes.extraLarge
            )
            .padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        Text(
            text = rating.formatRating(),
            color = MaterialTheme.colorScheme.inversePrimary
        )
    }
}

@Composable
private fun  String.formatRating(): String {
    val rating = stringResource(id = R.string.rating)
    return String.format("%s %s", rating, this)
}

@Preview
@Composable
private fun RatingTextPreview() {
    ThemeComposable {
        RatingText("3.0")
    }
}