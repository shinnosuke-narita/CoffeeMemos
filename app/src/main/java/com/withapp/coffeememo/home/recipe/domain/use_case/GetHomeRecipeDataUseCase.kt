package com.withapp.coffeememo.home.recipe.domain.use_case

import com.withapp.coffeememo.home.recipe.domain.model.HomeRecipeSource

interface GetHomeRecipeDataUseCase {
    suspend fun handle(): HomeRecipeSource
}