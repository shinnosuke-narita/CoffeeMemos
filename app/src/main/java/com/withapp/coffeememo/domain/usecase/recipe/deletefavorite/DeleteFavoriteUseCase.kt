package com.withapp.coffeememo.domain.usecase.recipe.deletefavorite

interface DeleteFavoriteUseCase {
    suspend fun handle(recipeId: Long)
}