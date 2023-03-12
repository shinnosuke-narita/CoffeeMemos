package com.example.coffeememos.create.recipe.domain.repository

import com.example.coffeememos.entity.Recipe
import com.example.coffeememos.entity.Taste

interface StorageRepository {
    suspend fun createRecipe(recipe: Recipe)
    suspend fun getNewestRecipeId(): Long
    suspend fun createTaste(taste: Taste)
}