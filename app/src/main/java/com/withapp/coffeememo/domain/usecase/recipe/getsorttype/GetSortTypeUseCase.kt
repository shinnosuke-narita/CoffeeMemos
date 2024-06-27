package com.withapp.coffeememo.domain.usecase.recipe.getsorttype

import com.withapp.coffeememo.search.recipe.domain.model.RecipeSortType

interface GetSortTypeUseCase {
    fun handle(targetIndex: Int): RecipeSortType
}