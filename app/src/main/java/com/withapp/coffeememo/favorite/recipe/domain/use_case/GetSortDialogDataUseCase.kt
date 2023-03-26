package com.withapp.coffeememo.favorite.recipe.domain.use_case

import com.withapp.coffeememo.favorite.recipe.domain.model.RecipeSortType
import com.withapp.coffeememo.favorite.recipe.domain.model.SortDialogOutput

interface GetSortDialogDataUseCase {
    fun handle(sortType: RecipeSortType): SortDialogOutput
}