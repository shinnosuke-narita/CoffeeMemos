package com.withapp.coffeememo.domain.usecase.recipe.updatefavorite

interface UpdateFavoriteUseCase {
    suspend fun handle(recipeId: Long, isFavorite: Boolean)
}