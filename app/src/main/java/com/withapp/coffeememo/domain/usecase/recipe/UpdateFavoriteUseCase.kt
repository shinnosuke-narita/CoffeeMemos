package com.withapp.coffeememo.domain.usecase.recipe

interface UpdateFavoriteUseCase {
    suspend fun handle(recipeId: Long, isFavorite: Boolean)
}