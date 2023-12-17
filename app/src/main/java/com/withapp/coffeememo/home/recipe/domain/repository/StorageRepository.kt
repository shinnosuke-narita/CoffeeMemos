package com.withapp.coffeememo.home.recipe.domain.repository

import com.withapp.coffeememo.home.recipe.domain.model.HomeRecipeModel

interface StorageRepository {
    suspend fun getHomeRecipeModel(): List<HomeRecipeModel>
    suspend fun updateFavorite(recipeId: Long, isFavorite: Boolean)
}