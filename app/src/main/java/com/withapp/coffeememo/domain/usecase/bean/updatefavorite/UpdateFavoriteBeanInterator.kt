package com.withapp.coffeememo.domain.usecase.bean.updatefavorite

import com.withapp.coffeememo.domain.repository.BeanRepository
import javax.inject.Inject

class UpdateFavoriteBeanInterator @Inject constructor(
    private var beanRepo: BeanRepository
) : UpdateFavoriteBeanUseCase {
    override suspend fun handle(beanId: Long, isFavorite: Boolean) {
        beanRepo.updateFavoriteByBeanId(beanId, isFavorite)
    }
}