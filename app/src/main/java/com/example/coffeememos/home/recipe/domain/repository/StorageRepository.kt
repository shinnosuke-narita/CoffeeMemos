package com.example.coffeememos.home.recipe.domain.repository

import com.example.coffeememos.home.recipe.domain.model.HomeRecipeModel

interface StorageRepository {
    suspend fun getHomeRecipeModel(): List<HomeRecipeModel>
    suspend fun updateFavorite(recipeId: Long, isFavorite: Boolean)
}