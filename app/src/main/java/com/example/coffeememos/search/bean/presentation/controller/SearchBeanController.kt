package com.example.coffeememos.search.bean.presentation.controller

import com.example.coffeememos.search.bean.domain.model.BeanSortType
import com.example.coffeememos.search.bean.domain.model.FilterBeanOutputData
import com.example.coffeememos.search.bean.domain.model.SearchBeanModel
import com.example.coffeememos.search.recipe.presentation.model.SearchKeyWord

interface SearchBeanController {
    fun getBeanOutPutData(key: String): FilterBeanOutputData?
    fun deleteBeanInputData(key: String)
    fun sortBean(sortType: BeanSortType, list: List<SearchBeanModel>): List<SearchBeanModel>
    suspend fun getAllBean(): List<SearchBeanModel>
    suspend fun freeWordSearch(freeWord: SearchKeyWord): List<SearchBeanModel>?
    suspend fun updateFavorite(beanId: Long, isFavorite: Boolean)
    suspend fun filter(
        searchWord: String,
        countryValues: List<String>,
        farmValues: List<String>,
        districtValues: List<String>,
        storeValues: List<String>,
        speciesValues: List<String>,
        ratingValues: List<Boolean>,
        processValues: List<Boolean>): List<SearchBeanModel>
}