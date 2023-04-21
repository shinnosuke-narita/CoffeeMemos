package com.withapp.coffeememo.favorite.recipe.presentation.presenter

import com.withapp.coffeememo.core.data.entity.Recipe
import com.withapp.coffeememo.favorite.recipe.presentation.model.FavoriteRecipeModel

interface FavoriteRecipePresenter {
    fun presentFavoriteRecipe(recipes: List<Recipe>): List<FavoriteRecipeModel>
}