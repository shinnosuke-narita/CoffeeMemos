package com.example.coffeememos.search.presentation.presenter

import com.example.coffeememos.entity.RecipeWithTaste
import com.example.coffeememos.search.domain.presenter.SearchRecipePresenter
import com.example.coffeememos.search.domain.model.SearchRecipeModel

class SearchRecipePresenter(private val mapper: SearchRecipeModelMapper) : SearchRecipePresenter {
    override fun presentFreeWordSearchRes(recipes: List<RecipeWithTaste>): List<SearchRecipeModel> {
        if (recipes.isEmpty()) return listOf()

        return mapper.execute(recipes)
    }
}