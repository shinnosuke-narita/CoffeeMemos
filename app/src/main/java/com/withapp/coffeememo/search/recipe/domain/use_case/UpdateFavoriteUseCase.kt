package com.withapp.coffeememo.search.recipe.domain.use_case

interface UpdateFavoriteUseCase {
    suspend fun handle(recipeId: Long, isFavorite: Boolean)
}