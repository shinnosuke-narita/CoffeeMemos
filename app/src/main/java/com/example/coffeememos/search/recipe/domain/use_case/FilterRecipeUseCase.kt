package com.example.coffeememos.search.recipe.domain.use_case

import com.example.coffeememos.search.recipe.domain.model.FilterRecipeInputData
import com.example.coffeememos.search.recipe.domain.model.SearchRecipeModel

interface FilterRecipeUseCase {
    suspend fun filterRecipe(inputData: FilterRecipeInputData): List<SearchRecipeModel>
}