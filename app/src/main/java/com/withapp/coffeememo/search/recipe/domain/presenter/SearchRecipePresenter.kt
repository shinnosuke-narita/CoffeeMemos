package com.withapp.coffeememo.search.recipe.domain.presenter

import com.withapp.coffeememo.search.recipe.domain.model.FilterRecipeInputData
import com.withapp.coffeememo.search.recipe.domain.model.FilterRecipeOutputData
import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel

interface SearchRecipePresenter {
    fun presentAllRecipes(recipes: List<SearchRecipeModel>): List<SearchRecipeModel>
    fun presentFreeWordSearchRes(recipes: List<SearchRecipeModel>): List<SearchRecipeModel>
    fun presentFilterResult(recipes: List<SearchRecipeModel>): List<SearchRecipeModel>
    fun presentFilterOutputData(inputData: FilterRecipeInputData): FilterRecipeOutputData
}