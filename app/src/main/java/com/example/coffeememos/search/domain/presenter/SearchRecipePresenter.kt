package com.example.coffeememos.search.domain.presenter

import com.example.coffeememos.search.domain.model.FilterRecipeInputData
import com.example.coffeememos.search.domain.model.FilterRecipeOutputData
import com.example.coffeememos.search.domain.model.SearchRecipeModel

interface SearchRecipePresenter {
    fun presentFreeWordSearchRes(recipes: List<SearchRecipeModel>): List<SearchRecipeModel>
    fun presentFilterResult(recipes: List<SearchRecipeModel>): List<SearchRecipeModel>
    fun presentFilterOutputData(inputData: FilterRecipeInputData): FilterRecipeOutputData
}