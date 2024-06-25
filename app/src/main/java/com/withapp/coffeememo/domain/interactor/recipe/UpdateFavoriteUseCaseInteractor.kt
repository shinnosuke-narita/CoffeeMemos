package com.withapp.coffeememo.domain.interactor.recipe

import com.withapp.coffeememo.domain.repository.RecipeRepository
import com.withapp.coffeememo.domain.usecase.recipe.UpdateFavoriteUseCase
import javax.inject.Inject

class UpdateFavoriteUseCaseInteractor @Inject constructor(
    private val recipeRepo: RecipeRepository
) : UpdateFavoriteUseCase {
    override suspend fun handle(recipeId: Long, isFavorite: Boolean) {
        recipeRepo.updateFavoriteByRecipeId(recipeId, isFavorite)
    }
}