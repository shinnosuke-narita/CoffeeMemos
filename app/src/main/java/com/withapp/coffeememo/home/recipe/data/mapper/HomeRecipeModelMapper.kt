package com.withapp.coffeememo.home.recipe.data.mapper

import com.withapp.coffeememo.entity.RecipeWithBeans
import com.withapp.coffeememo.home.recipe.domain.model.HomeRecipeModel

interface HomeRecipeModelMapper {
    fun execute(recipeWithBeans: List<RecipeWithBeans>): List<HomeRecipeModel>
}