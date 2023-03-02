package com.example.coffeememos.home.recipe.presentation.controller

import com.example.coffeememos.home.recipe.domain.presentation_model.HomeRecipeOutPut

interface HomeRecipeController {
    suspend fun getHomeRecipeData(): HomeRecipeOutPut
}