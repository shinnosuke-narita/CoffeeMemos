package com.withapp.coffeememo.domain.usecase.bean.getsorttype

import com.withapp.coffeememo.search.bean.domain.model.BeanSortType


interface GetSortTypeUseCase {
    fun handle(index: Int): BeanSortType
}