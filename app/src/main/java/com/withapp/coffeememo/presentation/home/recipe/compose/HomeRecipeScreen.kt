package com.withapp.coffeememo.presentation.home.recipe.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.withapp.coffeememo.R
import com.withapp.coffeememo.presentation.home.recipe.compose.components.HomeRecipeCard
import com.withapp.coffeememo.presentation.home.recipe.compose.components.HomeRecipeStatusCard
import com.withapp.coffeememo.presentation.home.recipe.model.HomeRecipeCardData
import com.withapp.coffeememo.presentation.home.recipe.view_model.HomeRecipeViewModel

@Composable
fun HomeRecipeScreen(
    viewModel: HomeRecipeViewModel = viewModel(),
    onCreateButton: () -> Unit
) {
    val newRecipes by viewModel.newRecipes.observeAsState()
    val favoriteRecipes by viewModel.favoriteRecipes.observeAsState()
    val highRatingRecipes by viewModel.highRatingRecipes.observeAsState()
    val totalCounts by viewModel.totalRecipeCount.observeAsState()
    val favoriteCounts by viewModel.favoriteRecipeCount.observeAsState()
    val todayRecipeCounts by viewModel.todayRecipeCount.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.size(dimensionResource(id = R.dimen.margin_small)))
        HomeRecipeStatusCard(
            allCounts = (totalCounts ?: 0).toString(),
            favoriteCounts = (favoriteCounts ?: 0).toString(),
            todayCounts = (todayRecipeCounts ?: 0).toString(),
            onCreateButton = onCreateButton
        )
        Spacer(Modifier.size(dimensionResource(id = R.dimen.margin_medium)))
        Text(
            text = stringResource(id = R.string.new_recipe_Header),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.size(dimensionResource(id = R.dimen.margin_small)))
        CardList(recipes = newRecipes)
        Spacer(Modifier.size(dimensionResource(id = R.dimen.margin_medium)))
        Text(
            text = stringResource(id = R.string.favorite_recipe_header),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.size(dimensionResource(id = R.dimen.margin_small)))
        CardList(recipes = favoriteRecipes)
        Spacer(Modifier.size(dimensionResource(id = R.dimen.margin_medium)))
        Text(
            text = stringResource(id = R.string.high_rating_header),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.size(dimensionResource(id = R.dimen.margin_small)))
        CardList(recipes = highRatingRecipes)
    }
}

@Composable
fun CardList(recipes: List<HomeRecipeCardData>?) {
    recipes?.let {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.margin_small)
            )
        ) {
            items(it) { recipe ->
                HomeRecipeCard(
                    recipe = recipe,
                    onClick = {}
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeRecipeScreenPreview() {
    HomeRecipeScreen(onCreateButton = {})
}