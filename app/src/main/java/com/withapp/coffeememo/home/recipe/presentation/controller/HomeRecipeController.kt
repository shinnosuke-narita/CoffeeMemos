package com.withapp.coffeememo.home.recipe.presentation.controller

import com.withapp.coffeememo.home.recipe.domain.model.HomeRecipeSource

interface HomeRecipeController {
    suspend fun getHomeRecipeData(): HomeRecipeSource
    suspend fun updateRecipeData(
        recipeId: Long,
        isFavorite: Boolean
    ): HomeRecipeSource
}