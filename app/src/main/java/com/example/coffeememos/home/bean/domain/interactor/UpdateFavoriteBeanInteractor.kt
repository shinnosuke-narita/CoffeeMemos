package com.example.coffeememos.home.bean.domain.interactor

import com.example.coffeememos.home.bean.domain.repository.StorageRepository
import com.example.coffeememos.home.bean.domain.use_case.UpdateFavoriteBeanUseCase
import javax.inject.Inject

class UpdateFavoriteBeanInteractor @Inject constructor()
    : UpdateFavoriteBeanUseCase {
    @Inject
    lateinit var repository: StorageRepository

    override suspend fun handle(beanId: Long, isFavorite: Boolean) {
        repository.updateFavorite(beanId, isFavorite)
    }
}