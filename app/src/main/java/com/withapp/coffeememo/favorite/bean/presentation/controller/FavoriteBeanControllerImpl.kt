package com.withapp.coffeememo.favorite.bean.presentation.controller

import com.withapp.coffeememo.core.data.entity.Bean
import com.withapp.coffeememo.favorite.bean.domain.model.BeanSortType
import com.withapp.coffeememo.favorite.bean.domain.model.FavoriteBeanModel
import com.withapp.coffeememo.favorite.bean.domain.use_case.*
import com.withapp.coffeememo.favorite.bean.presentation.presenter.FavoriteBeanPresenter
import com.withapp.coffeememo.favorite.recipe.domain.model.SortDialogOutput
import javax.inject.Inject

class FavoriteBeanControllerImpl @Inject constructor()
    : FavoriteBeanController {
    @Inject
    lateinit var presenter: FavoriteBeanPresenter
    @Inject
    lateinit var getFavoriteBeanUseCase: GetFavoriteBeanUseCase
    @Inject
    lateinit var deleteFavoriteUseCase: DeleteFavoriteUseCase
    @Inject
    lateinit var sortBeanUseCase: SortBeanUseCase
    @Inject
    lateinit var getSortDialogDataUseCase: GetSortDialogDataUseCase
    @Inject
    lateinit var getSortTypeUseCase: GetSortTypeUseCase

    override suspend fun getFavoriteBean(): List<FavoriteBeanModel> {
        val beanList: List<Bean> = getFavoriteBeanUseCase.handle()
        return presenter.presentFavoriteBeanModel(beanList)
    }

    override suspend fun deleteFavorite(id: Long) {
        deleteFavoriteUseCase.handle(id)
    }

    override fun sortBean(
        sortType: BeanSortType,
        list: List<FavoriteBeanModel>
    ): List<FavoriteBeanModel> {
       return sortBeanUseCase.handle(sortType, list)
    }

    override fun getSortDialogData(
        sortType: BeanSortType
    ): SortDialogOutput {
       return getSortDialogDataUseCase.handle(sortType)
    }

    override fun getSortType(index: Int): BeanSortType {
        return getSortTypeUseCase.handle(index)
    }
}