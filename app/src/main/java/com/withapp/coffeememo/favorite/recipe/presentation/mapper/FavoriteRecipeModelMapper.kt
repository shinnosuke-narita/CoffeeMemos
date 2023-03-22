package com.withapp.coffeememo.favorite.recipe.presentation.mapper

import com.withapp.coffeememo.entity.Recipe
import com.withapp.coffeememo.favorite.recipe.presentation.model.FavoriteRecipeModel

interface FavoriteRecipeModelMapper {
    fun execute(recipe: Recipe): FavoriteRecipeModel
}