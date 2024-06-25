package com.withapp.coffeememo.favorite.recipe.domain.use_case

import com.withapp.coffeememo.presentation.favorite.recipe.model.FavoriteRecipeModel

interface GetFavoriteRecipeUseCase {
    suspend fun handle(): List<FavoriteRecipeModel>
}