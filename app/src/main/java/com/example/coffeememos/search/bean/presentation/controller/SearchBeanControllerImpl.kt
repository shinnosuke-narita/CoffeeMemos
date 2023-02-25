package com.example.coffeememos.search.bean.presentation.controller

import com.example.coffeememos.search.bean.domain.model.BeanSortType
import com.example.coffeememos.search.bean.domain.model.FilterBeanInputData
import com.example.coffeememos.search.bean.domain.model.FilterBeanOutputData
import com.example.coffeememos.search.bean.domain.model.SearchBeanModel
import com.example.coffeememos.search.bean.domain.use_case.*
import com.example.coffeememos.search.recipe.presentation.model.SearchKeyWord
import com.example.coffeememos.search.recipe.presentation.model.SearchType
import javax.inject.Inject
import kotlin.Int as Int

class SearchBeanControllerImpl @Inject constructor()
    : SearchBeanController {
    @Inject
    lateinit var freeWordSearchUseCase: FreeWordSearchUseCase
    @Inject
    lateinit var sortBeanUseCase: SortBeanUseCase
    @Inject
    lateinit var filterBeanUseCase: FilterBeanUseCase
    @Inject
    lateinit var setBeanInputDataUseCase: SetFilterBeanInputDataUseCase
    @Inject
    lateinit var getBeanOutputDataUseCase: GetFilterBeanOutputDataUseCase
    @Inject
    lateinit var getAllBeanUseCase: GetAllBeanUseCase
    @Inject
    lateinit var deleteFilterInputDataUseCase: DeleteFilterBeanInputDataUseCase
    @Inject
    lateinit var updateFavoriteUseCase: UpdateFavoriteUseCase

    override suspend fun getAllBean(): List<SearchBeanModel> {
        return getAllBeanUseCase.getAllBean()
    }

    override suspend fun freeWordSearch(freeWord: SearchKeyWord): List<SearchBeanModel>? {
        if (freeWord.keyWord.isEmpty()) return null
        if (freeWord.type == SearchType.BEAN) return null

        return freeWordSearchUseCase.handle(freeWord.keyWord)
    }

    override fun sortBean(sortType: BeanSortType, list: List<SearchBeanModel>): List<SearchBeanModel> {
        return sortBeanUseCase.sort(sortType, list)
    }

    override suspend fun filter(
        searchWord: String,
        countryValues: List<String>,
        farmValues: List<String>,
        districtValues: List<String>,
        storeValues: List<String>,
        speciesValues: List<String>,
        ratingValues: List<Boolean>,
        processValues: List<Boolean>): List<SearchBeanModel> {

        val ratingData = mutableListOf<Int>()
        for ((i, isSelected) in ratingValues.withIndex()) {
           if (isSelected) { ratingData.add(i + 1) }
        }

        val processData = mutableListOf<Int>()
        for ((i, isSelected) in processValues.withIndex()) {
            if (isSelected) { processData.add(i) }
        }

        val inputData = FilterBeanInputData(
            keyWord = searchWord,
            countries = countryValues,
            farms = farmValues,
            districts = districtValues,
            stores = storeValues,
            species = speciesValues,
            rating = ratingData,
            process = processData
        )

        // 絞り込み要素を保存
        setBeanInputDataUseCase.execute("filterBeanInputData", inputData)

        // 絞り込み実行
        return filterBeanUseCase.filterBean(inputData)
    }

    override fun getBeanOutPutData(key: String): FilterBeanOutputData? {
        return getBeanOutputDataUseCase.execute(key)
    }

    override fun deleteBeanInputData(key: String) {
        deleteFilterInputDataUseCase.handle(key)
    }

    override suspend fun updateFavorite(beanId: Long, isFavorite: Boolean) {
        updateFavoriteUseCase.handle(beanId, isFavorite)
    }

}