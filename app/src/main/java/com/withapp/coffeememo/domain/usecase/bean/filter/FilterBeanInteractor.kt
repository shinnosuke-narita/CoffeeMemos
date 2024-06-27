package com.withapp.coffeememo.domain.usecase.bean.filter

import com.withapp.coffeememo.domain.repository.BeanRepository
import com.withapp.coffeememo.domain.model.bean.FilterBeanInputData
import com.withapp.coffeememo.domain.model.bean.SearchBeanModel
import javax.inject.Inject

class FilterBeanInteractor @Inject constructor(
    private val beanRepo: BeanRepository
) : FilterBeanUseCase {
    override suspend fun filterBean(inputData: FilterBeanInputData): List<SearchBeanModel> {
        val beans: List<SearchBeanModel> =
            if (inputData.keyWord.isNotEmpty()) {
                 beanRepo.getSearchBeanModelByKeyword(inputData.keyWord)
            } else {
                 beanRepo.getSearchBeanModel()
            }

        if (beans.isEmpty()) return beans

        // 原産地
        var result = getMatchedBeans(beans, inputData.countries) {
                bean, value -> bean.country.contains(value) }
        if (result.isEmpty())  return result

        // 農園
        result = getMatchedBeans(result, inputData.farms) {
            bean, data -> bean.farm.contains(data)
        }
        if (result.isEmpty()) return result

        // 地区
        result = getMatchedBeans(result, inputData.districts) {
            bean, data -> bean.district.contains(data)
        }
        if (result.isEmpty()) return result
        // お店
        result = getMatchedBeans(result, inputData.stores) {
            bean, data -> bean.store.contains(data)
        }
        if (result.isEmpty()) return result

        // 品種
        result = getMatchedBeans(result, inputData.species) {
            bean, data -> bean.species.contains(data)
        }
        if (result.isEmpty()) return result

        // 評価
        result = getMatchedBeans(result, inputData.rating) {
            bean, data -> bean.rating == data
        }
        if (result.isEmpty())  return result

        // 精製法
        result = getMatchedBeans(result, inputData.process) {
            bean, data -> bean.process == data
        }

        return result
    }

    private fun <T> getMatchedBeans(
        beans: List<SearchBeanModel>,
        filteringData: List<T>,
        isMatch: (bean: SearchBeanModel, value: T) -> Boolean
    ): List<SearchBeanModel> {
        if (filteringData.isEmpty()) return beans

        val res: MutableList<SearchBeanModel> = mutableListOf()
        for (bean in beans) {
            for (value in filteringData) {
                if (isMatch(bean, value)) {
                    res.add(bean)
                }
            }
        }

        return res
    }
}