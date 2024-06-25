package com.withapp.coffeememo.domain.usecase.recipe

import com.withapp.coffeememo.domain.model.recipe.RecipeSortType

interface GetSortTypeUseCase {
    fun handle(targetIndex: Int): RecipeSortType
}