package com.example.coffeememos.home.recipe.presentation.controller

import com.example.coffeememos.home.recipe.domain.presentation_model.HomeRecipeOutPut
import com.example.coffeememos.home.recipe.domain.use_case.GetHomeRecipeDataUseCase
import javax.inject.Inject

class HomeRecipeControllerImpl @Inject constructor()
    : HomeRecipeController {
    @Inject
    lateinit var getHomeRecipeDataUseCase: GetHomeRecipeDataUseCase

    override suspend fun getHomeRecipeData(): HomeRecipeOutPut {
        return getHomeRecipeDataUseCase.handle()
    }
}