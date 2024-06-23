package com.withapp.coffeememo.home.recipe.domain.interactor

import com.withapp.coffeememo.domain.repository.RecipeRepository
import com.withapp.coffeememo.home.recipe.domain.use_case.UpdateFavoriteUseCase
import javax.inject.Inject

class UpdateFavoriteUseCaseInteractor @Inject constructor(
    private val recipeRepo: RecipeRepository
) : UpdateFavoriteUseCase {
    override suspend fun handle(recipeId: Long, isFavorite: Boolean) {
        recipeRepo.updateFavoriteByRecipeId(recipeId, isFavorite)
    }
}