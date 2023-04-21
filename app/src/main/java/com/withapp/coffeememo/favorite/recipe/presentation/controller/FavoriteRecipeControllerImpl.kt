package com.withapp.coffeememo.favorite.recipe.presentation.controller

import com.withapp.coffeememo.core.data.entity.Recipe
import com.withapp.coffeememo.favorite.recipe.domain.model.RecipeSortType
import com.withapp.coffeememo.favorite.recipe.domain.model.SortDialogOutput
import com.withapp.coffeememo.favorite.recipe.domain.use_case.*
import com.withapp.coffeememo.favorite.recipe.presentation.model.FavoriteRecipeModel
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
    @Inject
    lateinit var sortRecipeUseCase: SortRecipeUseCase
    @Inject
    lateinit var getSortDialogDataUseCase: GetSortDialogDataUseCase
    @Inject
    lateinit var getSortTypeUseCase: GetSortTypeUseCase

    override suspend fun getFavoriteRecipe(): List<FavoriteRecipeModel> {
        val recipes: List<Recipe> = getFavoriteUseCase.handle()
        if (recipes.isEmpty()) return emptyList()

        return presenter.presentFavoriteRecipe(recipes)
    }

    override suspend fun deleteFavorite(recipeId: Long) {
        deleteFavoriteUseCase.handle(recipeId)
    }

    override fun sortRecipe(
        sortType: RecipeSortType,
        list: List<FavoriteRecipeModel>
    ): List<FavoriteRecipeModel> {
        return sortRecipeUseCase.handle(
           sortType, list
        )
    }

    override fun getSortDialogData(
        sortType: RecipeSortType
    ): SortDialogOutput {
        return getSortDialogDataUseCase.handle(sortType)
    }

    override fun getSortType(index: Int): RecipeSortType {
        return getSortTypeUseCase.handle(index)
    }
}