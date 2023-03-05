package com.example.coffeememos.home.recipe.presentation.mapper

import com.example.coffeememos.home.recipe.domain.model.HomeRecipeModel

interface HomeRecipeInfoMapper {
    fun execute(data: List<HomeRecipeModel>): List<com.example.coffeememos.home.recipe.presentation.model.HomeRecipeCardData>
}