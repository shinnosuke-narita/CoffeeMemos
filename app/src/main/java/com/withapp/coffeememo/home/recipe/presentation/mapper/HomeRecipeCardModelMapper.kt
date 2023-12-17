package com.withapp.coffeememo.home.recipe.presentation.mapper

import com.withapp.coffeememo.home.recipe.domain.model.HomeRecipeModel
import com.withapp.coffeememo.home.recipe.presentation.model.HomeRecipeCardData

interface HomeRecipeCardModelMapper {
    fun execute(data: List<HomeRecipeModel>): List<HomeRecipeCardData>
}