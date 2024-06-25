package com.withapp.coffeememo.domain.interactor.recipe

import com.withapp.coffeememo.domain.model.recipe.RecipeSortType
import com.withapp.coffeememo.domain.usecase.recipe.GetSortTypeUseCase
import javax.inject.Inject

class GetSortTypeInteractor @Inject constructor()
    : GetSortTypeUseCase {
    override fun handle(targetIndex: Int): RecipeSortType {
        return RecipeSortType.getFromIndex(targetIndex)
    }
}