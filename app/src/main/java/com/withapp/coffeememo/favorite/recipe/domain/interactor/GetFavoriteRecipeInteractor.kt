package com.withapp.coffeememo.favorite.recipe.domain.interactor

import com.withapp.coffeememo.domain.repository.RecipeRepository
import com.withapp.coffeememo.favorite.recipe.domain.use_case.GetFavoriteRecipeUseCase
import com.withapp.coffeememo.favorite.recipe.presentation.mapper.FavoriteRecipeModelMapper
import com.withapp.coffeememo.favorite.recipe.presentation.model.FavoriteRecipeModel
import javax.inject.Inject

class GetFavoriteRecipeInteractor @Inject constructor(
    private val recipeRepo: RecipeRepository,
    private val mapper: FavoriteRecipeModelMapper
) : GetFavoriteRecipeUseCase {
    override suspend fun handle(): List<FavoriteRecipeModel> {
        return recipeRepo.getFavoriteRecipe().map(mapper::execute)
    }
}