package com.withapp.coffeememo.favorite.recipe.domain.interactor

import com.withapp.coffeememo.favorite.recipe.domain.model.RecipeSortType
import com.withapp.coffeememo.favorite.recipe.domain.use_case.GetSortTypeUseCase
import javax.inject.Inject

class GetSortTypeInteractor @Inject constructor()
    : GetSortTypeUseCase {
    override fun handle(targetIndex: Int): RecipeSortType {
        return RecipeSortType.getFromIndex(targetIndex)
    }
}