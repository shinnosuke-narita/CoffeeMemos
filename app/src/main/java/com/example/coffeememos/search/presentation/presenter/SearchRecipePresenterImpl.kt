package com.example.coffeememos.search.presentation.presenter

import com.example.coffeememos.entity.RecipeWithTaste
import com.example.coffeememos.search.domain.presenter.SearchRecipePresenter
import com.example.coffeememos.search.domain.model.SearchRecipeModel
import javax.inject.Inject

class SearchRecipePresenterImpl @Inject constructor() : SearchRecipePresenter {
    @Inject
    lateinit var mapper: SearchRecipeModelMapper

    override fun presentFreeWordSearchRes(recipes: List<RecipeWithTaste>): List<SearchRecipeModel> {
        if (recipes.isEmpty()) return listOf()

        return mapper.execute(recipes)
    }
}