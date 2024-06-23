package com.withapp.coffeememo.search.bean.domain.interactor

import com.withapp.coffeememo.search.bean.domain.cache.BeanMemoryCache
import com.withapp.coffeememo.search.bean.domain.model.FilterBeanInputData
import com.withapp.coffeememo.search.bean.domain.model.FilterBeanOutputData
import com.withapp.coffeememo.search.bean.domain.presenter.SearchBeanPresenter
import com.withapp.coffeememo.search.bean.domain.serialization.BeanSerializer
import com.withapp.coffeememo.search.bean.domain.use_case.GetFilterBeanOutputDataUseCase
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