package com.withapp.coffeememo.favorite.recipe.domain.use_case

import com.withapp.coffeememo.favorite.recipe.domain.model.RecipeSortType
import com.withapp.coffeememo.favorite.recipe.presentation.model.FavoriteRecipeModel

interface SortRecipeUseCase {
    fun handle(
        sortType: RecipeSortType,
        list: List<FavoriteRecipeModel>
    ): List<FavoriteRecipeModel>
}