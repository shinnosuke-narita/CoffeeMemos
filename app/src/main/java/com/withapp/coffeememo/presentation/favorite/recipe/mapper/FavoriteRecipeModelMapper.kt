package com.withapp.coffeememo.presentation.favorite.recipe.mapper

import com.withapp.coffeememo.core.data.entity.Recipe
import com.withapp.coffeememo.presentation.favorite.recipe.model.FavoriteRecipeModel

interface FavoriteRecipeModelMapper {
    fun execute(recipe: Recipe): FavoriteRecipeModel
}