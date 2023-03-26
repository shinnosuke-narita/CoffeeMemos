package com.withapp.coffeememo.favorite.bean.presentation.controller

import com.withapp.coffeememo.entity.Bean
import com.withapp.coffeememo.favorite.bean.domain.model.FavoriteBeanModel
import com.withapp.coffeememo.favorite.bean.domain.use_case.GetFavoriteBeanUseCase
import com.withapp.coffeememo.favorite.bean.presentation.presenter.FavoriteBeanPresenter
import javax.inject.Inject

class FavoriteBeanControllerImpl @Inject constructor()
    : FavoriteBeanController {
    @Inject
    lateinit var presenter: FavoriteBeanPresenter
    @Inject
    lateinit var getFavoriteBeanUseCase: GetFavoriteBeanUseCase

    override suspend fun getFavoriteBean(): List<FavoriteBeanModel> {
        val beanList: List<Bean> = getFavoriteBeanUseCase.handle()
        return presenter.presentFavoriteBeanModel(beanList)
    }
}