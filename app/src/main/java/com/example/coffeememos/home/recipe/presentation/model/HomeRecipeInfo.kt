package com.example.coffeememos.home.recipe.presentation.model

data class HomeRecipeInfo(
    val recipeId  : Long,
    val beanId    : Long,
    val country   : String,
    val createdAt : String,
    val tool      : String,
    val roast     : String,
    val rating    : String,
    var isFavorite: Boolean
)