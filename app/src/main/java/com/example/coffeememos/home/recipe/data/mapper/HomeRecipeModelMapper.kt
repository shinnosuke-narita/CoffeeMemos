package com.example.coffeememos.home.recipe.data.mapper

import com.example.coffeememos.entity.RecipeWithBeans
import com.example.coffeememos.home.recipe.domain.model.HomeRecipeModel

interface HomeRecipeModelMapper {
    fun execute(recipeWithBeans: List<RecipeWithBeans>): List<HomeRecipeModel>
}