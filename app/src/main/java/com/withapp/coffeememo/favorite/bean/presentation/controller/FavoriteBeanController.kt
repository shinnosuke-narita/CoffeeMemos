package com.withapp.coffeememo.favorite.bean.presentation.controller

import com.withapp.coffeememo.favorite.bean.domain.model.BeanSortType
import com.withapp.coffeememo.favorite.bean.domain.model.FavoriteBeanModel

interface FavoriteBeanController {
    suspend fun getFavoriteBean(): List<FavoriteBeanModel>
    suspend fun deleteFavorite(id: Long)
    fun sortBean(
        sortType: BeanSortType,
        list: List<FavoriteBeanModel>
    ): List<FavoriteBeanModel>
    fun getSortType(index: Int): BeanSortType
}