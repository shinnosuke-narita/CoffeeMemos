package com.withapp.coffeememo.search.recipe.domain.use_case

import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel

interface GetAllRecipeUseCase {
    suspend fun getAllRecipe(): List<SearchRecipeModel>
}