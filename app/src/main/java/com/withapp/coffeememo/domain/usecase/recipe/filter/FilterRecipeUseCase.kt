package com.withapp.coffeememo.domain.usecase.recipe.filter

import com.withapp.coffeememo.domain.model.recipe.FilterRecipeInputData
import com.withapp.coffeememo.domain.model.recipe.SearchRecipeModel

interface FilterRecipeUseCase {
    suspend fun filterRecipe(inputData: FilterRecipeInputData): List<SearchRecipeModel>
}