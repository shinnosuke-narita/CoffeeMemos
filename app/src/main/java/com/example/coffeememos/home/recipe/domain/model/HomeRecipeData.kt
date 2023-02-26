package com.example.coffeememos.home.recipe.domain.model

data class HomeRecipeData(
    val newRecipes: List<HomeRecipeModel>,
    val highRatingRecipes: List<HomeRecipeModel>,
    val favoriteRecipes: List<HomeRecipeModel>,
    val totalCount: Int,
    val todayCount: Int,
)