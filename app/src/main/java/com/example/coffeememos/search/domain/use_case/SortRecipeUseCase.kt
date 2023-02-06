package com.example.coffeememos.search.domain.use_case

import com.example.coffeememos.search.domain.model.RecipeSortType
import com.example.coffeememos.search.domain.model.SearchRecipeModel

interface SortRecipeUseCase {
    fun sort(
        sortType: RecipeSortType,
        list: List<SearchRecipeModel>
    ): List<SearchRecipeModel>
}