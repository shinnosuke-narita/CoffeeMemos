package com.withapp.coffeememo.favorite.bean.domain.interactor

import com.withapp.coffeememo.domain.repository.BeanRepository
import com.withapp.coffeememo.favorite.bean.domain.model.FavoriteBeanModel
import com.withapp.coffeememo.favorite.bean.domain.use_case.GetFavoriteBeanUseCase
import com.withapp.coffeememo.favorite.bean.presentation.mapper.FavoriteBeanModelMapper
import javax.inject.Inject

class GetFavoriteBeanInteractor @Inject constructor(
    private val beanRepo: BeanRepository,
    private val mapper: FavoriteBeanModelMapper,
) : GetFavoriteBeanUseCase {
    override suspend fun handle(): List<FavoriteBeanModel> {
        return beanRepo.getFavoriteBean().map(mapper::execute)
    }
}