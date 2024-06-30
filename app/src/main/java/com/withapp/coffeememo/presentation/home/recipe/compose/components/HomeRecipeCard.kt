package com.withapp.coffeememo.presentation.home.recipe.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.withapp.coffeememo.R
import com.withapp.coffeememo.presentation.components.RatingText
import com.withapp.coffeememo.presentation.components.TagText
import com.withapp.coffeememo.presentation.components.ThemeComposable
import com.withapp.coffeememo.presentation.home.recipe.model.HomeRecipeCardData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun HomeRecipeCard(
    recipe: HomeRecipeCardData,
    onClick: (Long) -> Unit
) {
    val density = LocalDensity.current
    var width by remember { mutableStateOf(0.dp) }
    val widthRatio = 1.15f

    Column(
        modifier = Modifier
            .onGloballyPositioned {
                with(density) {
                    width = (it.size.height * widthRatio)
                        .toInt()
                        .toDp()
                }
            }
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = MaterialTheme.shapes.extraLarge
            )
            .clip(MaterialTheme.shapes.extraLarge)
            .clickable(
                onClick = { onClick(recipe.recipeId) }
            )
            .widthIn(width)
            .padding(dimensionResource(id = R.dimen.padding_large))
    ) {
        Text(
            text = recipe.tool,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.margin_small)))
        Text(
            text = recipe.createdAt.format(
                DateTimeFormatter.ofPattern(stringResource(id = R.string.date_pattern)),
           ),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.margin_medium)))
        TagText(text = recipe.country)
        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.margin_small)))
        TagText(text = recipe.roast)
        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.margin_medium)))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.widthIn(width)
        ) {
            RatingText(rating = recipe.rating)
            val iconRes =
                if (recipe.isFavorite)
                    R.drawable.ic_baseline_favorite_24
                else
                    R.drawable.ic_baseline_favorite_border_24
            Icon(
                painter = painterResource(id = iconRes),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = ""
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeRecipeCardPreview() {
    ThemeComposable {
        HomeRecipeCard(
            recipe = HomeRecipeCardData(
                recipeId = 0L,
                beanId = 0L,
                tool = "HARIO",
                createdAt = LocalDateTime.now(),
                country = "ブラジル",
                roast = "フルシティロースト",
                rating = "3.0",
                isFavorite = true

            ),
            onClick = {}
        )
    }
}