package com.example.coffeememos.home.recipe.presentation.presenter

import com.example.coffeememos.home.recipe.domain.model.HomeRecipeSource
import com.example.coffeememos.home.recipe.presentation.model.HomeRecipeOutput

interface HomeRecipePresenter {
    fun presentHomeRecipeData(homeRecipeData: HomeRecipeSource): HomeRecipeOutput
}