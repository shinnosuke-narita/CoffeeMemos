package com.withapp.coffeememo.search.bean.domain.use_case

import com.withapp.coffeememo.search.bean.domain.model.FilterBeanInputData
import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel

interface FilterBeanUseCase {
    suspend fun filterBean(inputData: FilterBeanInputData): List<SearchBeanModel>
}