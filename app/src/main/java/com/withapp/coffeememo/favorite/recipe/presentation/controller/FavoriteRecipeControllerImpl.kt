package com.withapp.coffeememo.favorite.recipe.presentation.controller

import com.withapp.coffeememo.entity.Recipe
import com.withapp.coffeememo.favorite.recipe.domain.use_case.DeleteFavoriteUseCase
import com.withapp.coffeememo.favorite.recipe.presentation.model.FavoriteRecipeModel
import com.withapp.coffeememo.favorite.recipe.domain.use_case.GetFavoriteRecipeUseCase
import com.withapp.coffeememo.favorite.recipe.presentation.presenter.FavoriteRecipePresenter
import javax.inject.Inject

class FavoriteRecipeControllerImpl @Inject constructor()
    : FavoriteRecipeController {
    @Inject
    lateinit var presenter: FavoriteRecipePresenter
    @Inject
    lateinit var getFavoriteUseCase: GetFavoriteRecipeUseCase
    @Inject
    lateinit var deleteFavoriteUseCase: DeleteFavoriteUseCase

    override suspend fun getFavoriteRecipe(): List<FavoriteRecipeModel> {
        val recipes: List<Recipe> = getFavoriteUseCase.handle()
        if (recipes.isEmpty()) return emptyList()

        return presenter.presentFavoriteRecipe(recipes)
    }

    override suspend fun deleteFavorite(recipeId: Long) {
        deleteFavoriteUseCase.handle(recipeId)
    }
}