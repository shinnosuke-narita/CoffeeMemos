package com.example.coffeememos.home.recipe.presentation.controller

import com.example.coffeememos.home.recipe.domain.model.HomeRecipeData
import com.example.coffeememos.home.recipe.domain.use_case.GetHomeRecipeDataUseCase
import com.example.coffeememos.home.recipe.domain.use_case.UpdateFavoriteUseCase
import javax.inject.Inject

class HomeRecipeControllerImpl @Inject constructor()
    : HomeRecipeController {
    @Inject
    lateinit var getHomeRecipeDataUseCase: GetHomeRecipeDataUseCase
    @Inject
    lateinit var updateFavoriteUseCase: UpdateFavoriteUseCase

    override suspend fun getHomeRecipeData(): HomeRecipeData {
        return getHomeRecipeDataUseCase.handle()
    }

    override suspend fun updateRecipeData(
        recipeId: Long,
        isFavorite: Boolean
    ): HomeRecipeData {
        updateFavoriteUseCase.handle(recipeId, isFavorite)
        return getHomeRecipeDataUseCase.handle()
    }
}