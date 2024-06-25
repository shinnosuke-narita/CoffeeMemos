package com.withapp.coffeememo.domain.interactor.bean

import com.withapp.coffeememo.domain.repository.BeanRepository
import com.withapp.coffeememo.domain.model.bean.FavoriteBeanModel
import com.withapp.coffeememo.domain.usecase.bean.GetFavoriteBeanUseCase
import com.withapp.coffeememo.presentation.favorite.bean.mapper.FavoriteBeanModelMapper
import javax.inject.Inject

class GetFavoriteBeanInteractor @Inject constructor(
    private val beanRepo: BeanRepository,
    private val mapper: FavoriteBeanModelMapper,
) : GetFavoriteBeanUseCase {
    override suspend fun handle(): List<FavoriteBeanModel> {
        return beanRepo.getFavoriteBean().map(mapper::execute)
    }
}