package com.example.coffeememos.search.bean.domain.use_case

import com.example.coffeememos.search.bean.domain.model.FilterBeanInputData

interface SetFilterBeanInputDataUseCase {
    fun execute(key: String, inputData: FilterBeanInputData)
}