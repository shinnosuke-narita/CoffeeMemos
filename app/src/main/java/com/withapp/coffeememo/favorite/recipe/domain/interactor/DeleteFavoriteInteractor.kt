package com.withapp.coffeememo.favorite.recipe.domain.interactor

import com.withapp.coffeememo.domain.repository.RecipeRepository
import com.withapp.coffeememo.favorite.recipe.domain.use_case.DeleteFavoriteUseCase
import javax.inject.Inject

class DeleteFavoriteInteractor @Inject constructor(
    private var recipeRepo: RecipeRepository
) : DeleteFavoriteUseCase {
    override suspend fun handle(recipeId: Long) {
        recipeRepo.updateFavoriteByRecipeId(recipeId, false)
    }
}