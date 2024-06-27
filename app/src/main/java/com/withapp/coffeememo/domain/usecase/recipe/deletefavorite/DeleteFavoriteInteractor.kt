package com.withapp.coffeememo.domain.usecase.recipe.deletefavorite

import com.withapp.coffeememo.domain.repository.RecipeRepository
import com.withapp.coffeememo.domain.usecase.recipe.deletefavorite.DeleteFavoriteUseCase
import javax.inject.Inject

class DeleteFavoriteInteractor @Inject constructor(
    private var recipeRepo: RecipeRepository
) : DeleteFavoriteUseCase {
    override suspend fun handle(recipeId: Long) {
        recipeRepo.updateFavoriteByRecipeId(recipeId, false)
    }
}