package com.withapp.coffeememo.domain.model.recipe

data class HomeRecipeSource(
    val newRecipes: List<HomeRecipeModel>,
    val highRatingRecipes: List<HomeRecipeModel>,
    val favoriteRecipes: List<HomeRecipeModel>,
    val totalCount: Int,
    val todayCount: Int,
)