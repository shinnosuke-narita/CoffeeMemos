package com.withapp.coffeememo.domain.usecase.bean.filter

import com.withapp.coffeememo.domain.model.bean.FilterBeanInputData
import com.withapp.coffeememo.domain.model.bean.SearchBeanModel

interface FilterBeanUseCase {
    suspend fun filterBean(inputData: FilterBeanInputData): List<SearchBeanModel>
}