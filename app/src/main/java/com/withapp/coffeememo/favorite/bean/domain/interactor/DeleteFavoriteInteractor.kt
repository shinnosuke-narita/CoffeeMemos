package com.withapp.coffeememo.favorite.bean.domain.interactor

import com.withapp.coffeememo.domain.repository.BeanRepository
import com.withapp.coffeememo.favorite.bean.domain.use_case.DeleteFavoriteUseCase
import javax.inject.Inject

class DeleteFavoriteInteractor @Inject constructor(
    private val beanRepo: BeanRepository
): DeleteFavoriteUseCase {
    override suspend fun handle(beanId: Long) {
        beanRepo.deleteFavoriteBean(beanId)
    }
}