package com.withapp.coffeememo.favorite.bean.domain.interactor

import com.withapp.coffeememo.core.data.entity.Bean
import com.withapp.coffeememo.domain.repository.BeanRepository
import com.withapp.coffeememo.favorite.bean.domain.use_case.GetFavoriteBeanUseCase
import javax.inject.Inject

class GetFavoriteBeanInteractor @Inject constructor(
    private val beanRepo: BeanRepository
) : GetFavoriteBeanUseCase {
    override suspend fun handle(): List<Bean> {
        return beanRepo.getFavoriteBean()
    }
}