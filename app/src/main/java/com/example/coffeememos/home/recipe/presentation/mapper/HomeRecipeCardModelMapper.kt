package com.example.coffeememos.home.recipe.presentation.mapper

import com.example.coffeememos.home.recipe.domain.model.HomeRecipeModel
import com.example.coffeememos.home.recipe.presentation.model.HomeRecipeCardData

interface HomeRecipeCardModelMapper {
    fun execute(data: List<HomeRecipeModel>): List<HomeRecipeCardData>
}