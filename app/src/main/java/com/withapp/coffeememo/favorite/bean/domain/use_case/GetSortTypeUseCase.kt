package com.withapp.coffeememo.favorite.bean.domain.use_case

import com.withapp.coffeememo.favorite.bean.domain.model.BeanSortType

interface GetSortTypeUseCase {
    fun handle(index: Int): BeanSortType
}