package com.withapp.coffeememo.domain.usecase.recipe.sortfavoriterecipe

import com.withapp.coffeememo.domain.model.recipe.RecipeSortType
import com.withapp.coffeememo.presentation.favorite.recipe.model.FavoriteRecipeModel

interface SortRecipeUseCase {
    fun handle(
        sortType: RecipeSortType,
        list: List<FavoriteRecipeModel>
    ): List<FavoriteRecipeModel>
}