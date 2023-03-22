package com.withapp.coffeememo.favorite.recipe.presentation.controller

import com.withapp.coffeememo.favorite.recipe.presentation.model.FavoriteRecipeModel

interface FavoriteRecipeController {
    suspend fun getFavoriteRecipe(): List<FavoriteRecipeModel>
    suspend fun deleteFavorite(recipeId: Long)
}