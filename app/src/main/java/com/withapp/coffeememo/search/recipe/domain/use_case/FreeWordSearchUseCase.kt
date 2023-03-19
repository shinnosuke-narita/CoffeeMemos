package com.withapp.coffeememo.search.recipe.domain.use_case

import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel

interface FreeWordSearchUseCase {
    suspend fun handle(freeWord: String): List<SearchRecipeModel>
}