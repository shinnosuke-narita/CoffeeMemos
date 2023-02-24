package com.example.coffeememos.search.recipe.domain.use_case

import com.example.coffeememos.search.recipe.domain.model.RecipeSortType
import com.example.coffeememos.search.recipe.domain.model.SearchRecipeModel

interface SortRecipeUseCase {
    fun sort(
        sortType: RecipeSortType,
        list: List<SearchRecipeModel>
    ): List<SearchRecipeModel>
}