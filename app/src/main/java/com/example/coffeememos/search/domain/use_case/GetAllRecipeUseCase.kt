package com.example.coffeememos.search.domain.use_case

import com.example.coffeememos.entity.RecipeWithTaste

interface GetAllRecipeUseCase {
    suspend fun getAllRecipe(): List<RecipeWithTaste>
}