package com.withapp.coffeememo.favorite.recipe.presentation.controller

import com.withapp.coffeememo.favorite.recipe.domain.model.RecipeSortType
import com.withapp.coffeememo.favorite.recipe.domain.model.SortDialogOutput
import com.withapp.coffeememo.favorite.recipe.presentation.model.FavoriteRecipeModel

interface FavoriteRecipeController {
    suspend fun getFavoriteRecipe(): List<FavoriteRecipeModel>
    suspend fun deleteFavorite(recipeId: Long)
    fun sortRecipe(
        sortType: RecipeSortType,
        list: List<FavoriteRecipeModel>
    ): List<FavoriteRecipeModel>
    fun getSortDialogData(sortType: RecipeSortType): SortDialogOutput
    fun getSortType(index: Int): RecipeSortType
}