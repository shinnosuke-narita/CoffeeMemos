package com.withapp.coffeememo.favorite.recipe.domain.use_case

interface DeleteFavoriteUseCase {
    suspend fun handle(recipeId: Long)
}