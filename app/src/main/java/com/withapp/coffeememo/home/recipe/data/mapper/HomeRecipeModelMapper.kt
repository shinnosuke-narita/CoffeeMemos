package com.withapp.coffeememo.home.recipe.data.mapper

import com.withapp.coffeememo.core.data.entity.RecipeWithBeans
import com.withapp.coffeememo.domain.model.recipe.HomeRecipeModel

interface HomeRecipeModelMapper {
    fun execute(recipeWithBeans: List<RecipeWithBeans>): List<HomeRecipeModel>
}