package com.example.coffeememos.search.domain.use_case

interface UpdateFavoriteUseCase {
    suspend fun handle(recipeId: Long, isFavorite: Boolean)
}