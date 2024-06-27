package com.withapp.coffeememo.domain.usecase.recipe.sort

import com.withapp.coffeememo.domain.model.recipe.RecipeSortType
import com.withapp.coffeememo.domain.model.recipe.SearchRecipeModel

interface SortRecipeUseCase {
    fun sort(
        sortType: RecipeSortType,
        list: List<SearchRecipeModel>
    ): List<SearchRecipeModel>
}