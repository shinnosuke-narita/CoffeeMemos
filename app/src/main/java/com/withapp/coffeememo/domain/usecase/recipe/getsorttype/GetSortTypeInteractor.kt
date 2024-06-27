package com.withapp.coffeememo.domain.usecase.recipe.getsorttype

import com.withapp.coffeememo.search.recipe.domain.model.RecipeSortType
import javax.inject.Inject

class GetSortTypeInteractor @Inject constructor()
    : GetSortTypeUseCase {
    override fun handle(targetIndex: Int): RecipeSortType {
        return RecipeSortType.from(targetIndex)
    }
}