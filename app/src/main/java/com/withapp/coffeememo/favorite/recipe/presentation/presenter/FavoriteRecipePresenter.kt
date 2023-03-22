package com.withapp.coffeememo.favorite.recipe.presentation.presenter

import com.withapp.coffeememo.entity.Recipe
import com.withapp.coffeememo.favorite.recipe.presentation.model.FavoriteRecipeModel

interface FavoriteRecipePresenter {
    fun presentFavoriteRecipe(recipes: List<Recipe>): List<FavoriteRecipeModel>
}