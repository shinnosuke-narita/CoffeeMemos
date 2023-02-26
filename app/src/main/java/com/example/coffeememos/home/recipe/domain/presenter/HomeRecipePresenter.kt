package com.example.coffeememos.home.recipe.domain.presenter

import com.example.coffeememos.home.recipe.domain.model.HomeRecipeData
import com.example.coffeememos.home.recipe.domain.presentation_model.HomeRecipeOutPut

interface HomeRecipePresenter {
    fun presentHomeRecipeData(homeRecipeData: HomeRecipeData): HomeRecipeOutPut
}