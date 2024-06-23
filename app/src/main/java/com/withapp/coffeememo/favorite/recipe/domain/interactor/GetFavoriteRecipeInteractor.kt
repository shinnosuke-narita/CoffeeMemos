package com.withapp.coffeememo.favorite.recipe.domain.interactor

import com.withapp.coffeememo.core.data.entity.Recipe
import com.withapp.coffeememo.domain.repository.RecipeRepository
import com.withapp.coffeememo.favorite.recipe.domain.use_case.GetFavoriteRecipeUseCase
import javax.inject.Inject

class GetFavoriteRecipeInteractor @Inject constructor(
    private val recipeRepo: RecipeRepository
) : GetFavoriteRecipeUseCase {
    override suspend fun handle(): List<Recipe> {
        return recipeRepo.getFavoriteRecipe()
    }
}