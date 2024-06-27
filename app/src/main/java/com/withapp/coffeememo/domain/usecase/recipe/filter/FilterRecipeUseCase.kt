package com.withapp.coffeememo.domain.usecase.recipe.filter

import com.withapp.coffeememo.search.recipe.domain.model.FilterRecipeInputData
import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel

interface FilterRecipeUseCase {
    suspend fun filterRecipe(inputData: FilterRecipeInputData): List<SearchRecipeModel>
}