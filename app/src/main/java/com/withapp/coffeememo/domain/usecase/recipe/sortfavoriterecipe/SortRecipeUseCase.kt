package com.withapp.coffeememo.domain.usecase.recipe.sortfavoriterecipe

import com.withapp.coffeememo.presentation.favorite.recipe.model.FavoriteRecipeModel
import com.withapp.coffeememo.search.recipe.domain.model.RecipeSortType

interface SortRecipeUseCase {
    fun handle(
        sortType: RecipeSortType,
        list: List<FavoriteRecipeModel>
    ): List<FavoriteRecipeModel>
}