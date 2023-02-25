package com.example.coffeememos.search.bean.domain.use_case

import com.example.coffeememos.search.bean.domain.model.FilterBeanInputData
import com.example.coffeememos.search.bean.domain.model.SearchBeanModel

interface FilterBeanUseCase {
    suspend fun filterBean(inputData: FilterBeanInputData): List<SearchBeanModel>
}