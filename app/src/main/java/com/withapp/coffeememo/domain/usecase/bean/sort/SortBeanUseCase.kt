package com.withapp.coffeememo.domain.usecase.bean.sort

import com.withapp.coffeememo.domain.model.bean.BeanSortType
import com.withapp.coffeememo.domain.model.bean.SearchBeanModel

interface SortBeanUseCase {
    fun sort(
        sortType: BeanSortType,
        list: List<SearchBeanModel>
    ): List<SearchBeanModel>
}