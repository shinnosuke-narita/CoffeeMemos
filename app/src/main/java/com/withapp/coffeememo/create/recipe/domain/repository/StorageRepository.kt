package com.withapp.coffeememo.create.recipe.domain.repository

import com.withapp.coffeememo.core.data.entity.Recipe
import com.withapp.coffeememo.core.data.entity.Taste

interface StorageRepository {
    suspend fun createRecipe(recipe: Recipe)
    suspend fun getNewestRecipeId(): Long
    suspend fun createTaste(taste: Taste)
    suspend fun getBeanCount(): Int
}