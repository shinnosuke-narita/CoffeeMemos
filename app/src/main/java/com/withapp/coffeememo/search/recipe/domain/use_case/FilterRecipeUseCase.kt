package com.withapp.coffeememo.search.recipe.domain.use_case

import com.withapp.coffeememo.search.recipe.domain.model.FilterRecipeInputData
import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel

interface FilterRecipeUseCase {
    suspend fun filterRecipe(inputData: FilterRecipeInputData): List<SearchRecipeModel>
}