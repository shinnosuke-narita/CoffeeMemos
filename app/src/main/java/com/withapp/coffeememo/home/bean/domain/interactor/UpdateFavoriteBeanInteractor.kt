package com.withapp.coffeememo.home.bean.domain.interactor

import com.withapp.coffeememo.home.bean.domain.repository.StorageRepository
import com.withapp.coffeememo.home.bean.domain.use_case.UpdateFavoriteBeanUseCase
import javax.inject.Inject

class UpdateFavoriteBeanInteractor @Inject constructor()
    : UpdateFavoriteBeanUseCase {
    @Inject
    lateinit var repository: StorageRepository

    override suspend fun handle(beanId: Long, isFavorite: Boolean) {
        repository.updateFavorite(beanId, isFavorite)
    }
}