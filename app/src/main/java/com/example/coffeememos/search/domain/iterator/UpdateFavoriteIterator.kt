package com.example.coffeememos.search.domain.iterator

import com.example.coffeememos.search.domain.repository.SearchRecipeDiskRepository
import com.example.coffeememos.search.domain.use_case.UpdateFavoriteUseCase
import javax.inject.Inject

class UpdateFavoriteIterator @Inject constructor()
    : UpdateFavoriteUseCase {
    @Inject
    lateinit var repository: SearchRecipeDiskRepository

    override suspend fun handle(recipeId: Long, isFavorite: Boolean) {
        repository.updateFavorite(recipeId, isFavorite)
    }
}