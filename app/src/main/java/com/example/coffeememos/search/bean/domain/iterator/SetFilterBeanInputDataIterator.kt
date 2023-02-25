package com.example.coffeememos.search.bean.domain.iterator

import com.example.coffeememos.search.bean.domain.cache.BeanMemoryCache
import com.example.coffeememos.search.bean.domain.model.FilterBeanInputData
import com.example.coffeememos.search.bean.domain.serialization.BeanSerializer
import com.example.coffeememos.search.bean.domain.use_case.SetFilterBeanInputDataUseCase
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