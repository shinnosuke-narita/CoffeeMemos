package com.example.coffeememos.home.recipe.domain.presentation_model

import com.example.coffeememos.home.recipe.presentation.model.HomeRecipeInfo

data class HomeRecipeOutPut(
    val newRecipes: List<HomeRecipeInfo>,
    val highRatingRecipes: List<HomeRecipeInfo>,
    val favoriteRecipes: List<HomeRecipeInfo>,
    val totalCount: Int,
    val todayCount: Int,
    val favoriteCount: Int,
)