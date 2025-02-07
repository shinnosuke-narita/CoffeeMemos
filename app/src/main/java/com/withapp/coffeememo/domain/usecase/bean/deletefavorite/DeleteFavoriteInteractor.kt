package com.withapp.coffeememo.domain.usecase.bean.deletefavorite

import com.withapp.coffeememo.domain.repository.BeanRepository
import com.withapp.coffeememo.domain.usecase.bean.deletefavorite.DeleteFavoriteUseCase
import javax.inject.Inject

class DeleteFavoriteInteractor @Inject constructor(
    private val beanRepo: BeanRepository
): DeleteFavoriteUseCase {
    override suspend fun handle(beanId: Long) {
        beanRepo.deleteFavoriteBean(beanId)
    }
}