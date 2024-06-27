package com.withapp.coffeememo.domain.usecase.bean.sort

import com.withapp.coffeememo.search.bean.domain.model.BeanSortType
import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel

interface SortBeanUseCase {
    fun sort(
        sortType: BeanSortType,
        list: List<SearchBeanModel>
    ): List<SearchBeanModel>
}