package com.withapp.coffeememo.domain.usecase.recipe.getall

import com.withapp.coffeememo.domain.model.recipe.SearchRecipeModel

interface GetAllRecipeUseCase {
    suspend fun getAllRecipe(): List<SearchRecipeModel>
}