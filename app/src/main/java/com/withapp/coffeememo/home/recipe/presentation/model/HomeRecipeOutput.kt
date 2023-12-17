package com.withapp.coffeememo.home.recipe.presentation.model

data class HomeRecipeOutput(
    val newRecipes: List<HomeRecipeCardData>,
    val highRatingRecipes: List<HomeRecipeCardData>,
    val favoriteRecipes: List<HomeRecipeCardData>,
    val totalCount: Int,
    val todayCount: Int,
    val favoriteCount: Int,
)