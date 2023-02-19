package com.example.coffeememos.search.domain.use_case

import com.example.coffeememos.search.domain.model.FilterRecipeInputData
import com.example.coffeememos.search.domain.model.SearchRecipeModel

interface FilterRecipeUseCase {
    suspend fun filterRecipe(inputData: FilterRecipeInputData): List<SearchRecipeModel>
}