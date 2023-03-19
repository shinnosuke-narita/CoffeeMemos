package com.withapp.coffeememo.home.recipe.presentation.controller

import com.withapp.coffeememo.home.recipe.domain.model.HomeRecipeSource
import com.withapp.coffeememo.home.recipe.domain.use_case.GetHomeRecipeDataUseCase
import com.withapp.coffeememo.home.recipe.domain.use_case.UpdateFavoriteUseCase
import javax.inject.Inject

class HomeRecipeControllerImpl @Inject constructor()
    : HomeRecipeController {
    @Inject
    lateinit var getHomeRecipeDataUseCase: GetHomeRecipeDataUseCase
    @Inject
    lateinit var updateFavoriteUseCase: UpdateFavoriteUseCase

    override suspend fun getHomeRecipeData(): HomeRecipeSource {
        return getHomeRecipeDataUseCase.handle()
    }

    override suspend fun updateRecipeData(
        recipeId: Long,
        isFavorite: Boolean
    ): HomeRecipeSource {
        updateFavoriteUseCase.handle(recipeId, isFavorite)
        return getHomeRecipeDataUseCase.handle()
    }
}