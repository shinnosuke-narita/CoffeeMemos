package com.withapp.coffeememo.search.bean.domain.use_case

import com.withapp.coffeememo.search.bean.domain.model.FilterBeanInputData

interface SetFilterBeanInputDataUseCase {
    fun execute(key: String, inputData: FilterBeanInputData)
}