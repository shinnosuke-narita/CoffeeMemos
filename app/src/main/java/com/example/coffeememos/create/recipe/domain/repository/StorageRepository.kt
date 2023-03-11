package com.example.coffeememos.create.recipe.domain.repository

import com.example.coffeememos.entity.Recipe
import com.example.coffeememos.entity.Taste

interface StorageRepository {
    fun createRecipe(recipe: Recipe)
    fun createTaste(taste: Taste)
}