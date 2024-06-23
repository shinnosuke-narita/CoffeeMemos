package com.withapp.coffeememo.search.bean.domain.interactor

import com.withapp.coffeememo.search.bean.domain.cache.BeanMemoryCache
import com.withapp.coffeememo.search.bean.domain.model.FilterBeanInputData
import com.withapp.coffeememo.search.bean.domain.serialization.BeanSerializer
import com.withapp.coffeememo.search.bean.domain.use_case.SetFilterBeanInputDataUseCase
import javax.inject.Inject

class SetFilterBeanInputDataIterator @Inject constructor()
    : SetFilterBeanInputDataUseCase {
    @Inject
    lateinit var memoryCache: BeanMemoryCache
    @Inject
    lateinit var serializer: BeanSerializer

    override fun execute(key: String, inputData: FilterBeanInputData) {
        val jsonStr = serializer.serialize(inputData)
        memoryCache.setData(key, jsonStr)
    }

}