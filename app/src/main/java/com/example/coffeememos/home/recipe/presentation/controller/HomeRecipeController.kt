package com.example.coffeememos.home.recipe.presentation.controller

import com.example.coffeememos.home.recipe.domain.model.HomeRecipeSource

interface HomeRecipeController {
    suspend fun getHomeRecipeData(): HomeRecipeSource
    suspend fun updateRecipeData(
        recipeId: Long,
        isFavorite: Boolean
    ): HomeRecipeSource
}