package com.withapp.coffeememo.create.recipe.domain.repository

import com.withapp.coffeememo.entity.Recipe
import com.withapp.coffeememo.entity.Taste

interface StorageRepository {
    suspend fun createRecipe(recipe: Recipe)
    suspend fun getNewestRecipeId(): Long
    suspend fun createTaste(taste: Taste)
    suspend fun getBeanCount(): Int
}