package com.withapp.coffeememo.home.recipe.domain.model

data class HomeRecipeSource(
    val newRecipes: List<HomeRecipeModel>,
    val highRatingRecipes: List<HomeRecipeModel>,
    val favoriteRecipes: List<HomeRecipeModel>,
    val totalCount: Int,
    val todayCount: Int,
)