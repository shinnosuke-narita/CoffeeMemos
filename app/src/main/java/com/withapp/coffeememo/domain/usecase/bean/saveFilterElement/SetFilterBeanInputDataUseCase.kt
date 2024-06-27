package com.withapp.coffeememo.domain.usecase.bean.saveFilterElement

import com.withapp.coffeememo.search.bean.domain.model.FilterBeanInputData

interface SetFilterBeanInputDataUseCase {
    fun execute(key: String, inputData: FilterBeanInputData)
}