package com.withapp.coffeememo.favorite.bean.domain.use_case

import com.withapp.coffeememo.favorite.bean.domain.model.BeanSortType
import com.withapp.coffeememo.favorite.bean.domain.model.FavoriteBeanModel

interface SortBeanUseCase {
    fun handle(
        sortType: BeanSortType,
        list: List<FavoriteBeanModel>
    ): List<FavoriteBeanModel>
}