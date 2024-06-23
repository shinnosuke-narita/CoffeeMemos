package com.withapp.coffeememo.search.bean.domain.interactor

import com.withapp.coffeememo.search.bean.domain.cache.BeanMemoryCache
import com.withapp.coffeememo.search.bean.domain.model.FilterBeanInputData
import com.withapp.coffeememo.search.bean.domain.model.FilterBeanOutputData
import com.withapp.coffeememo.search.bean.domain.serialization.BeanSerializer
import com.withapp.coffeememo.search.bean.domain.use_case.GetFilterBeanOutputDataUseCase
import javax.inject.Inject

class GetFilterBeanInputDataIterator @Inject constructor(
    private val deserializer: BeanSerializer,
    private val memoryCache: BeanMemoryCache
) : GetFilterBeanOutputDataUseCase {
    override  fun execute(key: String): FilterBeanOutputData? {
        val jsonStr = memoryCache.getData(key)
        if (jsonStr.isEmpty()) {
            return null
        }
        val inputData: FilterBeanInputData = deserializer.deserialize(jsonStr) ?: return null

        val ratingValues: MutableList<Boolean> = MutableList(5) {false}
        val processValues: MutableList<Boolean> = MutableList(5) {false}
        convertInputData(
            inputData = inputData.process,
            outPutData = processValues,
            shouldConvertIndex = false
        )
        convertInputData(
            inputData = inputData.rating,
            outPutData = ratingValues,
            shouldConvertIndex = true
        )

        return FilterBeanOutputData(
            countries = inputData.countries,
            farms = inputData.farms,
            districts = inputData.districts,
            stores = inputData.stores,
            species = inputData.species,
            rating = ratingValues,
            process = processValues
        )
    }

    private fun convertInputData(
        inputData: List<Int>,
        outPutData: MutableList<Boolean>,
        shouldConvertIndex: Boolean) {
        if (inputData.isEmpty()) return

        for(inputValue in inputData) {
            var index: Int = inputValue
            if (shouldConvertIndex) {
                index--
            }

            outPutData[index] = true
        }
    }
}