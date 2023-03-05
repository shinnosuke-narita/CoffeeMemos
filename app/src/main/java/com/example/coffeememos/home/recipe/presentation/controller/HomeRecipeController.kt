package com.example.coffeememos.home.recipe.presentation.controller

import com.example.coffeememos.home.recipe.domain.model.HomeRecipeData

interface HomeRecipeController {
    suspend fun getHomeRecipeData(): HomeRecipeData
    suspend fun updateRecipeData(
        recipeId: Long,
        isFavorite: Boolean
    ): HomeRecipeData
}