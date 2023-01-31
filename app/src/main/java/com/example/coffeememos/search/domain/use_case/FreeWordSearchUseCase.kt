package com.example.coffeememos.search.domain.use_case

import com.example.coffeememos.search.domain.model.SearchRecipeModel

interface FreeWordSearchUseCase {
    suspend fun handle(freeWord: String): List<SearchRecipeModel>
}