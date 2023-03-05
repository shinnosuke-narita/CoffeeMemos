package com.example.coffeememos.home.recipe.presentation.presenter

import com.example.coffeememos.home.recipe.domain.model.HomeRecipeData
import com.example.coffeememos.home.recipe.presentation.model.HomeRecipeOutPut

interface HomeRecipePresenter {
    fun presentHomeRecipeData(homeRecipeData: HomeRecipeData): HomeRecipeOutPut
}