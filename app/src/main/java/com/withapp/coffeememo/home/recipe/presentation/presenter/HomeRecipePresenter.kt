package com.withapp.coffeememo.home.recipe.presentation.presenter

import com.withapp.coffeememo.home.recipe.domain.model.HomeRecipeSource
import com.withapp.coffeememo.home.recipe.presentation.model.HomeRecipeOutput

interface HomeRecipePresenter {
    fun presentHomeRecipeData(homeRecipeData: HomeRecipeSource): HomeRecipeOutput
}