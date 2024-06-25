package com.withapp.coffeememo.domain.interactor.bean

import com.withapp.coffeememo.domain.repository.BeanRepository
import com.withapp.coffeememo.domain.usecase.bean.UpdateFavoriteBeanUseCase
import javax.inject.Inject

class UpdateFavoriteBeanInteractor @Inject constructor(
    private val beanRepo: BeanRepository
) : UpdateFavoriteBeanUseCase {
    override suspend fun handle(beanId: Long, isFavorite: Boolean) {
        beanRepo.updateFavoriteByBeanId(beanId, isFavorite)
    }
}