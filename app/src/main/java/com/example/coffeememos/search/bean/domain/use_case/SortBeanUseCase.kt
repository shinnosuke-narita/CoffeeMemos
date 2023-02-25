package com.example.coffeememos.search.bean.domain.use_case

import com.example.coffeememos.search.bean.domain.model.BeanSortType
import com.example.coffeememos.search.bean.domain.model.SearchBeanModel

interface SortBeanUseCase {
    fun sort(
        sortType: BeanSortType,
        list: List<SearchBeanModel>
    ): List<SearchBeanModel>
}