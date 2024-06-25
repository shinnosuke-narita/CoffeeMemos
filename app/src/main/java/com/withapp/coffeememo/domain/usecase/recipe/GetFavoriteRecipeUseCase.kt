package com.withapp.coffeememo.domain.usecase.recipe

import com.withapp.coffeememo.presentation.favorite.recipe.model.FavoriteRecipeModel

interface GetFavoriteRecipeUseCase {
    suspend fun handle(): List<FavoriteRecipeModel>
}