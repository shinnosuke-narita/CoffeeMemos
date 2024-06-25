package com.withapp.coffeememo.domain.interactor.recipe

import com.withapp.coffeememo.domain.repository.RecipeRepository
import com.withapp.coffeememo.domain.usecase.recipe.GetFavoriteRecipeUseCase
import com.withapp.coffeememo.presentation.favorite.recipe.mapper.FavoriteRecipeModelMapper
import com.withapp.coffeememo.presentation.favorite.recipe.model.FavoriteRecipeModel
import javax.inject.Inject

class GetFavoriteRecipeInteractor @Inject constructor(
    private val recipeRepo: RecipeRepository,
    private val mapper: FavoriteRecipeModelMapper
) : GetFavoriteRecipeUseCase {
    override suspend fun handle(): List<FavoriteRecipeModel> {
        return recipeRepo.getFavoriteRecipe().map(mapper::execute)
    }
}