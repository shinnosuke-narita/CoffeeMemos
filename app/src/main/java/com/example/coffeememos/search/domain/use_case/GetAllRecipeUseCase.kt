package com.example.coffeememos.search.domain.use_case

import com.example.coffeememos.search.domain.model.SearchRecipeModel

interface GetAllRecipeUseCase {
    suspend fun getAllRecipe(): List<SearchRecipeModel>
}