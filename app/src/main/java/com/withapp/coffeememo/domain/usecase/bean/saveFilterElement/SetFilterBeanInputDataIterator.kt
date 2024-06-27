package com.withapp.coffeememo.domain.usecase.bean.saveFilterElement

import com.withapp.coffeememo.domain.repository.BeanCacheRepository
import com.withapp.coffeememo.domain.model.bean.FilterBeanInputData
import com.withapp.coffeememo.domain.serialization.bean.BeanSerializer
import javax.inject.Inject

class SetFilterBeanInputDataIterator @Inject constructor()
    : SetFilterBeanInputDataUseCase {
    @Inject
    lateinit var memoryCache: BeanCacheRepository
    @Inject
    lateinit var serializer: BeanSerializer

    override fun execute(key: String, inputData: FilterBeanInputData) {
        val jsonStr = serializer.serialize(inputData)
        memoryCache.setData(key, jsonStr)
    }

}