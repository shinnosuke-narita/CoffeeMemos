package com.example.coffeememos.search.bean.domain.iterator

import com.example.coffeememos.search.bean.domain.cache.BeanMemoryCache
import com.example.coffeememos.search.bean.domain.model.FilterBeanInputData
import com.example.coffeememos.search.bean.domain.model.FilterBeanOutputData
import com.example.coffeememos.search.bean.domain.presenter.SearchBeanPresenter
import com.example.coffeememos.search.bean.domain.serialization.BeanSerializer
import com.example.coffeememos.search.bean.domain.use_case.GetFilterBeanOutputDataUseCase
import javax.inject.Inject

class GetFilterBeanInputDataIterator @Inject constructor()
    : GetFilterBeanOutputDataUseCase {
    @Inject
    lateinit var deserializer: BeanSerializer
    @Inject
    lateinit var memoryCache: BeanMemoryCache
    @Inject
    lateinit var presenter: SearchBeanPresenter

    override  fun execute(key: String): FilterBeanOutputData? {
        val jsonStr = memoryCache.getData(key)
        if (jsonStr.isEmpty()) {
            return null
        }

        val inputData: FilterBeanInputData =
            deserializer.deserialize(jsonStr)
                ?: return null

        return presenter.presentFilterOutputData(inputData)
    }
}