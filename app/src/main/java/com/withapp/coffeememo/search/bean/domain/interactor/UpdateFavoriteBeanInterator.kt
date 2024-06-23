package com.withapp.coffeememo.search.bean.domain.interactor

import com.withapp.coffeememo.domain.repository.BeanRepository
import com.withapp.coffeememo.search.bean.domain.use_case.UpdateFavoriteBeanUseCase
import javax.inject.Inject

class UpdateFavoriteBeanInterator @Inject constructor(
    private var beanRepo: BeanRepository
) : UpdateFavoriteBeanUseCase {
    override suspend fun handle(beanId: Long, isFavorite: Boolean) {
        beanRepo.updateFavoriteByBeanId(beanId, isFavorite)
    }
}