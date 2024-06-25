package com.withapp.coffeememo.domain.usecase.recipe

import com.withapp.coffeememo.domain.model.recipe.HomeRecipeSource

interface GetHomeRecipeDataUseCase {
    suspend fun handle(): HomeRecipeSource
}