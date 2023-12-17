package com.withapp.coffeememo.favorite.recipe.domain.use_case

import com.withapp.coffeememo.favorite.recipe.domain.model.RecipeSortType

interface GetSortTypeUseCase {
    fun handle(targetIndex: Int): RecipeSortType
}