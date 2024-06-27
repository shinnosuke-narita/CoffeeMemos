package com.withapp.coffeememo.domain.usecase.recipe.getall

import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel

interface GetAllRecipeUseCase {
    suspend fun getAllRecipe(): List<SearchRecipeModel>
}