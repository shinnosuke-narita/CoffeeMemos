package com.example.coffeememos.search.recipe.domain.use_case

import com.example.coffeememos.search.recipe.domain.model.SearchRecipeModel

interface GetAllRecipeUseCase {
    suspend fun getAllRecipe(): List<SearchRecipeModel>
}