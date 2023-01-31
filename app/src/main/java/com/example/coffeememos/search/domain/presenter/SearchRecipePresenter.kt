package com.example.coffeememos.search.domain.presenter

import com.example.coffeememos.entity.RecipeWithTaste
import com.example.coffeememos.search.domain.model.SearchRecipeModel

interface SearchRecipePresenter {
    fun presentFreeWordSearchRes(recipes: List<RecipeWithTaste>): List<SearchRecipeModel>
}