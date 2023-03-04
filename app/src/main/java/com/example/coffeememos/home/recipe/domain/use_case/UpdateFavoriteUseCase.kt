package com.example.coffeememos.home.recipe.domain.use_case

interface UpdateFavoriteUseCase {
    suspend fun handle(recipeId: Long, isFavorite: Boolean)
}