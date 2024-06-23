package com.withapp.coffeememo.home.bean.domain.interactor

import com.withapp.coffeememo.domain.repository.BeanRepository
import com.withapp.coffeememo.home.bean.domain.use_case.UpdateFavoriteBeanUseCase
import javax.inject.Inject

class UpdateFavoriteBeanInteractor @Inject constructor(
    private val beanRepo: BeanRepository
) : UpdateFavoriteBeanUseCase {
    override suspend fun handle(beanId: Long, isFavorite: Boolean) {
        beanRepo.updateFavoriteByBeanId(beanId, isFavorite)
    }
}