package com.example.coffeememos.home.recipe.domain.use_case

import com.example.coffeememos.home.recipe.domain.model.HomeRecipeData

interface GetHomeRecipeDataUseCase {
    suspend fun handle(): HomeRecipeData
}