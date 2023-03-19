package com.withapp.coffeememo.search.bean.presentation.presenter

import com.withapp.coffeememo.search.bean.domain.model.FilterBeanInputData
import com.withapp.coffeememo.search.bean.domain.model.FilterBeanOutputData
import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel
import com.withapp.coffeememo.search.bean.domain.presenter.SearchBeanPresenter
import javax.inject.Inject

class SearchBeanPresenterImpl @Inject constructor()
    : SearchBeanPresenter {
    override fun presentAllBeans(beans: List<SearchBeanModel>): List<SearchBeanModel> {
        return beans
    }

    override fun presentFreeWordSearchRes(
        beans: List<SearchBeanModel>
    ): List<SearchBeanModel> {
        if (beans.isEmpty()) return listOf()

        return beans
    }

    override fun presentFilterResult(
        beans: List<SearchBeanModel>
    ): List<SearchBeanModel> {
        return beans
    }

    override fun presentFilterOutputData(
        inputData: FilterBeanInputData
    ): FilterBeanOutputData {
        val ratingValues: MutableList<Boolean> = MutableList(5) {false}
        val processValues: MutableList<Boolean> = MutableList(5) {false}

        convertInputData(
            inputData.process,
            processValues) { index, value ->
            index == value
        }

        convertInputData(
            inputData.rating,
            ratingValues) { index, value ->
            index == value
        }

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
        isMatch: (index: Int, value: Int) -> Boolean) {
        if (inputData.isEmpty()) return

        for ((i, _) in outPutData.withIndex()) {
            for (inputValue in inputData) {
                if (isMatch(i, inputValue)) {
                    outPutData[i] = true
                }
            }
        }

    }
}