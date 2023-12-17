package com.withapp.coffeememo.favorite.recipe.domain.repository

import com.withapp.coffeememo.core.data.entity.Recipe

interface StorageRepository {
    suspend fun getFavoriteRecipe(): List<Recipe>
    suspend fun deleteFavorite(recipeId: Long)
}