package com.withapp.coffeememo.presentation.home.recipe.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.withapp.coffeememo.R
import com.withapp.coffeememo.presentation.home.common.components.HomeHeader
import com.withapp.coffeememo.presentation.home.recipe.compose.components.HomeRecipeCard
import com.withapp.coffeememo.presentation.home.recipe.compose.components.HomeRecipeStatusCard
import com.withapp.coffeememo.presentation.home.recipe.model.HomeRecipeCardData
import com.withapp.coffeememo.presentation.home.recipe.view_model.HomeRecipeViewModel
import com.withapp.coffeememo.presentation.ui.theme.CoffeeMemoAppDefaults

@Composable
fun HomeRecipeScreen(
    viewModel: HomeRecipeViewModel = viewModel(),
    onCreateButton: () -> Unit,
    onClickHomeBeanFAB: () -> Unit,
    onCardClick: (HomeRecipeCardData) -> Unit,
    onFavoriteClick: (HomeRecipeCardData) -> Unit,
) {
    val newRecipes by viewModel.newRecipes.observeAsState()
    val favoriteRecipes by viewModel.favoriteRecipes.observeAsState()
    val highRatingRecipes by viewModel.highRatingRecipes.observeAsState()
    val totalCounts by viewModel.totalRecipeCount.observeAsState()
    val favoriteCounts by viewModel.favoriteRecipeCount.observeAsState()
    val todayRecipeCounts by viewModel.todayRecipeCount.observeAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(CoffeeMemoAppDefaults.Padding.medium)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.size(CoffeeMemoAppDefaults.Margin.small))
            HomeRecipeStatusCard(
                allCounts = (totalCounts ?: 0).toString(),
                favoriteCounts = (favoriteCounts ?: 0).toString(),
                todayCounts = (todayRecipeCounts ?: 0).toString(),
                onCreateButton = onCreateButton
            )
            Spacer(Modifier.size(CoffeeMemoAppDefaults.Margin.medium))
            HomeRow(
                title = stringResource(id = R.string.new_recipe_Header),
                recipes = newRecipes,
                onCardClick = onCardClick,
                onFavoriteClick = onFavoriteClick
            )
            Spacer(Modifier.size(CoffeeMemoAppDefaults.Margin.medium))
            HomeRow(
                title = stringResource(id = R.string.favorite_recipe_header),
                recipes = favoriteRecipes,
                onCardClick = onCardClick,
                onFavoriteClick = onFavoriteClick
            )
            Spacer(Modifier.size(CoffeeMemoAppDefaults.Margin.medium))
            HomeRow(
                title = stringResource(id = R.string.high_rating_header),
                recipes = highRatingRecipes,
                onCardClick = onCardClick,
                onFavoriteClick = onFavoriteClick
            )
            Spacer(Modifier.size(CoffeeMemoAppDefaults.Margin.extraLargeX))
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    PaddingValues(
                        top = 0.dp,
                        start = 0.dp,
                        end = CoffeeMemoAppDefaults.Margin.medium,
                        bottom = CoffeeMemoAppDefaults.Margin.medium,
                    )
                )
            ,
            onClick = { onClickHomeBeanFAB() },
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.coffee_bag_icon) ,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(CoffeeMemoAppDefaults.IconSize.default)
                )
            }
        )

    }
}


@Composable
fun HomeRow(
    title: String,
    recipes: List<HomeRecipeCardData>?,
    onCardClick: (HomeRecipeCardData) -> Unit,
    onFavoriteClick: (HomeRecipeCardData) -> Unit
) {
    HomeHeader(text = title)
    Spacer(Modifier.size(CoffeeMemoAppDefaults.Margin.small))
    recipes?.let {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(CoffeeMemoAppDefaults.Margin.small)
        ) {
            items(it) { recipe ->
                HomeRecipeCard(
                    recipe = recipe,
                    onCardClick = onCardClick,
                    onFavoriteClick = onFavoriteClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeRecipeScreenPreview() {
    HomeRecipeScreen(
        onCreateButton = {},
        onClickHomeBeanFAB = {},
        onFavoriteClick = {},
        onCardClick = {}
    )
}