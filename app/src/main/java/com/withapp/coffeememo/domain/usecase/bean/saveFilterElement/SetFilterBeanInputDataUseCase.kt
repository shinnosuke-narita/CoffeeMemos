package com.withapp.coffeememo.domain.usecase.bean.saveFilterElement

import com.withapp.coffeememo.domain.model.bean.FilterBeanInputData

interface SetFilterBeanInputDataUseCase {
    fun execute(key: String, inputData: FilterBeanInputData)
}