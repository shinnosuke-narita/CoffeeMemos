package com.withapp.coffeememo.presentation.home.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.withapp.coffeememo.R
import com.withapp.coffeememo.presentation.ui.theme.AppTypography
import com.withapp.coffeememo.presentation.ui.theme.SmallLabelGray

@Composable
fun CountStatus(title: String, counts: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = counts,
            style = AppTypography.headlineMedium
        )
        Text(
            text = title,
            style = SmallLabelGray
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CountStatusPreview() {
    CountStatus(
        title = stringResource(id = R.string.favorite_recipe_header),
        counts = "30"
    )
}