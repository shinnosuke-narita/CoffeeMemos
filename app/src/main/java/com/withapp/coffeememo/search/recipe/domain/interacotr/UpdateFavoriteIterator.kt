package com.withapp.coffeememo.search.recipe.domain.interacotr

import com.withapp.coffeememo.domain.repository.RecipeRepository
import com.withapp.coffeememo.search.recipe.domain.use_case.UpdateFavoriteUseCase
import javax.inject.Inject

class UpdateFavoriteIterator @Inject constructor(
    private var recipeRepo: RecipeRepository
) : UpdateFavoriteUseCase {
    override suspend fun handle(recipeId: Long, isFavorite: Boolean) {
        recipeRepo.updateFavoriteByRecipeId(recipeId, isFavorite)
    }
}