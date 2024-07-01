package com.withapp.coffeememo.presentation.home.recipe.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.withapp.coffeememo.R
import com.withapp.coffeememo.presentation.home.common.components.CountStatus
import com.withapp.coffeememo.presentation.ui.theme.AppTheme
import com.withapp.coffeememo.presentation.ui.theme.AppTypography
import com.withapp.coffeememo.presentation.ui.theme.CoffeeMemoAppDefaults

@Composable
fun HomeRecipeStatusCard(
    favoriteCounts: String,
    allCounts: String,
    todayCounts: String,
    onCreateButton: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = MaterialTheme.shapes.extraLarge
            )
            .padding(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.recipe_status),
            modifier = Modifier.align(Alignment.Start),
            style = AppTypography.headlineSmall
        )
        Spacer(modifier = Modifier.size(size = CoffeeMemoAppDefaults.Margin.medium))
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            CountStatus(
                title = stringResource(id = R.string.label_all),
                counts = allCounts
            )
            VerticalDivider(
                modifier = Modifier.size(20.dp)
            )
            CountStatus(
                title = stringResource(id = R.string.label_favorite),
                counts = favoriteCounts
            )
            VerticalDivider(
                modifier = Modifier.size(20.dp)
            )
            CountStatus(
                title = stringResource(id = R.string.label_today),
                counts = todayCounts
            )
        }

        Spacer(modifier = Modifier.size(CoffeeMemoAppDefaults.Margin.medium)
        )
        Button(
            onClick = onCreateButton,
            content = {
                Text(
                    text = stringResource(id = R.string.create_recipe),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )
    }

}

@Preview(showBackground = true)
@Composable
fun HomeRecipeStatusCardPreview() {
    AppTheme {
        Surface {
            HomeRecipeStatusCard(
                allCounts = "30",
                favoriteCounts = "20",
                todayCounts = "10",
                onCreateButton = {}
            )
        }
    }
}

