package com.withapp.coffeememo.domain.interactor.recipe

import com.withapp.coffeememo.domain.repository.RecipeRepository
import com.withapp.coffeememo.domain.usecase.recipe.DeleteFavoriteUseCase
import javax.inject.Inject

class DeleteFavoriteInteractor @Inject constructor(
    private var recipeRepo: RecipeRepository
) : DeleteFavoriteUseCase {
    override suspend fun handle(recipeId: Long) {
        recipeRepo.updateFavoriteByRecipeId(recipeId, false)
    }
}