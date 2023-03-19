package com.withapp.coffeememo.home.bean.presentation.controller

import com.withapp.coffeememo.home.bean.domain.model.HomeBeanSource
import com.withapp.coffeememo.home.bean.domain.use_case.GetHomeBeanDataUseCase
import com.withapp.coffeememo.home.bean.domain.use_case.UpdateFavoriteBeanUseCase
import com.withapp.coffeememo.home.bean.presentation.model.HomeBeanOutput
import com.withapp.coffeememo.home.bean.presentation.presenter.HomeBeanPresenter
import javax.inject.Inject

class HomeBeanControllerImpl @Inject constructor()
    : HomeBeanController {
    @Inject
    lateinit var getHomeBeanDataUseCase: GetHomeBeanDataUseCase
    @Inject
    lateinit var presenter: HomeBeanPresenter
    @Inject
    lateinit var updateFavoriteUseCase: UpdateFavoriteBeanUseCase

    override suspend fun getHomeBeanData(): HomeBeanOutput {
        val homeDataSource: HomeBeanSource =
            getHomeBeanDataUseCase.handle()

        return presenter.presentHomeBeanData(homeDataSource)
    }

    override suspend fun updateBeanData(
        beanId: Long,
        isFavorite: Boolean
    ): HomeBeanOutput {
        updateFavoriteUseCase.handle(beanId, isFavorite)
        val homeBeanSource = getHomeBeanDataUseCase.handle()

        return presenter.presentHomeBeanData(homeBeanSource)
    }
}