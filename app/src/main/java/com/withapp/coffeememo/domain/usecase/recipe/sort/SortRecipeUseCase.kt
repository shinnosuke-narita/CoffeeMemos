package com.withapp.coffeememo.domain.usecase.recipe.sort

import com.withapp.coffeememo.search.recipe.domain.model.RecipeSortType
import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel

interface SortRecipeUseCase {
    fun sort(
        sortType: RecipeSortType,
        list: List<SearchRecipeModel>
    ): List<SearchRecipeModel>
}