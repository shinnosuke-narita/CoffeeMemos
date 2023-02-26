package com.example.coffeememos.home.recipe.domain.use_case

import com.example.coffeememos.home.recipe.domain.presentation_model.HomeRecipeOutPut

interface GetHomeRecipeDataUseCase {
    suspend fun handle(): HomeRecipeOutPut
}