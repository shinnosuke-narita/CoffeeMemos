package com.withapp.coffeememo.favorite.bean.domain.interactor

import com.withapp.coffeememo.favorite.bean.domain.repository.StorageRepository
import com.withapp.coffeememo.favorite.bean.domain.use_case.DeleteFavoriteUseCase
import javax.inject.Inject

class DeleteFavoriteInteractor @Inject constructor()
    : DeleteFavoriteUseCase {
    @Inject
    lateinit var repository: StorageRepository

    override suspend fun handle(beanId: Long) {
        repository.deleteFavoriteBean(beanId)
    }
}