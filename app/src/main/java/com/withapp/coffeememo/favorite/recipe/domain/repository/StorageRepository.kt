package com.withapp.coffeememo.favorite.recipe.domain.repository

import com.withapp.coffeememo.entity.Recipe

interface StorageRepository {
    suspend fun getFavoriteRecipe(): List<Recipe>
    suspend fun deleteFavorite(recipeId: Long)
}