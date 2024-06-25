package com.withapp.coffeememo.domain.usecase.bean

import com.withapp.coffeememo.domain.model.bean.BeanSortType

interface GetSortTypeUseCase {
    fun handle(index: Int): BeanSortType
}