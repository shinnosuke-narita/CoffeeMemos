package com.example.coffeememos.home.recipe.domain.interactor

import com.example.coffeememos.home.recipe.domain.repository.StorageRepository
import com.example.coffeememos.home.recipe.domain.use_case.UpdateFavoriteUseCase
import javax.inject.Inject

class UpdateFavoriteUseCaseInteractor @Inject constructor()
    : UpdateFavoriteUseCase {
    @Inject
    lateinit var repository: StorageRepository

    override suspend fun handle(recipeId: Long, isFavorite: Boolean) {
        repository.updateFavorite(recipeId, isFavorite)
    }
}