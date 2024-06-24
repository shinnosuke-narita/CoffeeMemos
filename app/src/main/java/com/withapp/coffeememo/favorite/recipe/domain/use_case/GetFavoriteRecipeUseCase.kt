package com.withapp.coffeememo.favorite.recipe.domain.use_case

import com.withapp.coffeememo.favorite.recipe.presentation.model.FavoriteRecipeModel

interface GetFavoriteRecipeUseCase {
    suspend fun handle(): List<FavoriteRecipeModel>
}