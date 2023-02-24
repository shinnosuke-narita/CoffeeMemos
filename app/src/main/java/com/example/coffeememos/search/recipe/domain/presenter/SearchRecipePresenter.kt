package com.example.coffeememos.search.recipe.domain.presenter

import com.example.coffeememos.search.recipe.domain.model.FilterRecipeInputData
import com.example.coffeememos.search.recipe.domain.model.FilterRecipeOutputData
import com.example.coffeememos.search.recipe.domain.model.SearchRecipeModel

interface SearchRecipePresenter {
    fun presentAllRecipes(recipes: List<SearchRecipeModel>): List<SearchRecipeModel>
    fun presentFreeWordSearchRes(recipes: List<SearchRecipeModel>): List<SearchRecipeModel>
    fun presentFilterResult(recipes: List<SearchRecipeModel>): List<SearchRecipeModel>
    fun presentFilterOutputData(inputData: FilterRecipeInputData): FilterRecipeOutputData
}