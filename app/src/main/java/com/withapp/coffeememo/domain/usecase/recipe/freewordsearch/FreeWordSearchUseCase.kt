package com.withapp.coffeememo.domain.usecase.recipe.freewordsearch

import com.withapp.coffeememo.domain.model.recipe.SearchRecipeModel

interface FreeWordSearchUseCase {
    suspend fun handle(freeWord: String): List<SearchRecipeModel>
}