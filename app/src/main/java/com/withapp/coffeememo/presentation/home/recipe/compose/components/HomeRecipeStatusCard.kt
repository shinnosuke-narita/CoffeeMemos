package com.withapp.coffeememo.presentation.home.recipe.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.withapp.coffeememo.R
import org.intellij.lang.annotations.JdkConstants

@Composable
fun HomeRecipeStatusCard(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(id = R.string.reci;))
        Spacer(modifier = Modifier.size())

    }

}

@Preview
@Composable
fun HomeRecipeStatusCardPreview() {
    HomeRecipeStatusCard()
}

