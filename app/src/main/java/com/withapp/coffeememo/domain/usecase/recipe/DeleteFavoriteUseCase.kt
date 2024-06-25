package com.withapp.coffeememo.domain.usecase.recipe

interface DeleteFavoriteUseCase {
    suspend fun handle(recipeId: Long)
}