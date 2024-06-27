package com.withapp.coffeememo.domain.usecase.recipe.updatefavorite

import com.withapp.coffeememo.domain.repository.RecipeRepository
import com.withapp.coffeememo.domain.usecase.recipe.updatefavorite.UpdateFavoriteUseCase
import javax.inject.Inject

class UpdateFavoriteInteractor @Inject constructor(
    private var recipeRepo: RecipeRepository
) : UpdateFavoriteUseCase {
    override suspend fun handle(recipeId: Long, isFavorite: Boolean) {
        recipeRepo.updateFavoriteByRecipeId(recipeId, isFavorite)
    }
}