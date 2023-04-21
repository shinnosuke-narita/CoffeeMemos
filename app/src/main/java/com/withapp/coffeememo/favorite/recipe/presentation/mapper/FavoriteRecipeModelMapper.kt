package com.withapp.coffeememo.favorite.recipe.presentation.mapper

import com.withapp.coffeememo.core.data.entity.Recipe
import com.withapp.coffeememo.favorite.recipe.presentation.model.FavoriteRecipeModel

interface FavoriteRecipeModelMapper {
    fun execute(recipe: Recipe): FavoriteRecipeModel
}