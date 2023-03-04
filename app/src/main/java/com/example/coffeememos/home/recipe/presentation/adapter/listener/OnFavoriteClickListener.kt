package com.example.coffeememos.home.recipe.presentation.adapter.listener

import com.example.coffeememos.home.recipe.presentation.model.HomeRecipeInfo

interface OnFavoriteClickListener {
    fun onFavoriteClick(recipe: HomeRecipeInfo)
}