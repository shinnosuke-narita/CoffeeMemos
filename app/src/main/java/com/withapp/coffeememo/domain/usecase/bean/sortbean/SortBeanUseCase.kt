package com.withapp.coffeememo.domain.usecase.bean.sortbean

import com.withapp.coffeememo.domain.model.bean.FavoriteBeanModel
import com.withapp.coffeememo.domain.model.bean.BeanSortType

interface SortBeanUseCase {
    fun handle(
        sortType: BeanSortType,
        list: List<FavoriteBeanModel>
    ): List<FavoriteBeanModel>
}