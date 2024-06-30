package com.withapp.coffeememo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.withapp.coffeememo.R

@Composable
fun TagText(text: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.inversePrimary,
                shape = MaterialTheme.shapes.large
            )
            .padding(dimensionResource(R.dimen.padding_small))
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.inverseOnSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TagTextPreview() {
    ThemeComposable {
        TagText("フルシティロースト")
    }
}

