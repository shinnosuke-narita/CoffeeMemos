package com.withapp.coffeememo.favorite.bean.presentation.controller

import com.withapp.coffeememo.favorite.bean.domain.model.BeanSortType
import com.withapp.coffeememo.favorite.bean.domain.model.FavoriteBeanModel
import com.withapp.coffeememo.favorite.recipe.domain.model.SortDialogOutput

interface FavoriteBeanController {
    suspend fun getFavoriteBean(): List<FavoriteBeanModel>
    suspend fun deleteFavorite(id: Long)
    fun sortBean(
        sortType: BeanSortType,
        list: List<FavoriteBeanModel>
    ): List<FavoriteBeanModel>
    fun getSortDialogData(sortType: BeanSortType): SortDialogOutput
    fun getSortType(index: Int): BeanSortType
}