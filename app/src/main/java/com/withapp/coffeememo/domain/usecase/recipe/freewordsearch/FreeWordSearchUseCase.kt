package com.withapp.coffeememo.domain.usecase.recipe.freewordsearch

import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel

interface FreeWordSearchUseCase {
    suspend fun handle(freeWord: String): List<SearchRecipeModel>
}