package com.example.coffeememos.home.recipe.domain.use_case

import com.example.coffeememos.home.recipe.domain.model.HomeRecipeSource

interface GetHomeRecipeDataUseCase {
    suspend fun handle(): HomeRecipeSource
}