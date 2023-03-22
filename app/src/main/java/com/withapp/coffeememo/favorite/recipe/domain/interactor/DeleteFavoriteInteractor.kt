package com.withapp.coffeememo.favorite.recipe.domain.interactor

import com.withapp.coffeememo.favorite.recipe.domain.repository.StorageRepository
import com.withapp.coffeememo.favorite.recipe.domain.use_case.DeleteFavoriteUseCase
import javax.inject.Inject

class DeleteFavoriteInteractor @Inject constructor()
    : DeleteFavoriteUseCase {
    @Inject
    lateinit var repository: StorageRepository

    override suspend fun handle(recipeId: Long) {
        repository.deleteFavorite(recipeId)
    }
}