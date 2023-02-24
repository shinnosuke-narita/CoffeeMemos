package com.example.coffeememos.search.recipe.domain.use_case

import com.example.coffeememos.search.recipe.domain.model.SearchRecipeModel

interface FreeWordSearchUseCase {
    suspend fun handle(freeWord: String): List<SearchRecipeModel>
}