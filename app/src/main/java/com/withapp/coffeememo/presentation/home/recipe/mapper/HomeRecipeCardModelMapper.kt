package com.withapp.coffeememo.presentation.home.recipe.mapper

import com.withapp.coffeememo.domain.model.recipe.HomeRecipeModel
import com.withapp.coffeememo.presentation.home.recipe.model.HomeRecipeCardData

interface HomeRecipeCardModelMapper {
    fun execute(data: List<HomeRecipeModel>): List<HomeRecipeCardData>
}